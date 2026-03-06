package com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Clock;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ConnectionPool class manages a pool of database connections for efficient reuse.
 * It initializes a specified number of connections based on the configuration and
 * provides methods to acquire and release connections. The pool ensures that the
 * number of active connections does not exceed the configured pool size and handles
 * connection creation and closure gracefully. It also provides methods to retrieve
 * pool statistics for monitoring purposes.
 *
 * <p>The ConnectionPool class is designed to be a singleton, ensuring that only one
 * instance of the connection pool exists throughout the application. This design
 * allows for centralized management of database connections. The class uses JDBC
 * for connection management and includes error handling to manage exceptions that
 * may occur during connection creation and closure.</p>
 *
 * <p><b>Configuration keys</b> (aligned with your properties):
 * <ul>
 *   <li>db.url</li>
 *   <li>db.username</li>
 *   <li>db.password</li>
 *   <li>db.driver</li>
 *   <li>db.hikari.maximum-pool-size</li>
 *   <li>db.hikari.minimum-idle</li>
 *   <li>db.hikari.connection-timeout</li>
 *   <li>db.hikari.idle-timeout</li>
 *   <li>db.hikari.max-lifetime</li>
 *   <li>db.hikari.pool-name</li>
 *   <li>db.hikari.connection-test-query</li>
 * </ul>
 * </p>
 *
 * @author Developer
 * @version 1.0.0
 * @since 16.0
 */
public final class ConnectionPool {

    private static final Logger logger = Logger.getLogger(ConnectionPool.class.getName());

    // ---- Singleton holder ----
    private static ConnectionPool instance;

    // ---- Configuration ----
    private final String jdbcUrl;
    private final String username;
    private final String password;
    private final String driverClass;

    private final int poolSize;                 // maximum pool size
    private final int minimumIdle;              // initial & maintained idle target (best-effort)
    private final long connectionTimeoutMs;     // wait timeout for getConnection
    private final long idleTimeoutMs;           // retire idle connections after this
    private final long maxLifetimeMs;           // retire connections older than this
    private final String poolName;
    private final String testQuery;

    // ---- Internal state ----
    private final Deque<Connection> availableConnections = new ArrayDeque<>();
    private final List<Connection> usedConnections = new ArrayList<>();

    // Track lifecycle for retirement (by identity)
    private final Map<Connection, Long> birthTimes = new IdentityHashMap<>();
    private final Map<Connection, Long> lastReleasedTimes = new IdentityHashMap<>();

    private volatile boolean closed = false;
    private final Clock clock = Clock.systemUTC();

    // ------------------------------------------------------------
    // Construction / Singleton
    // ------------------------------------------------------------

    /**
     * Private constructor to initialize the connection pool based on the configuration.
     *
     * @throws SQLException if there is an error initializing the connection pool
     */
    private ConnectionPool(Properties props) throws SQLException {
        Objects.requireNonNull(props, "properties must not be null");

        this.jdbcUrl = trimToNull(props.getProperty("db.url"));
        this.username = props.getProperty("db.username", "");
        this.password = props.getProperty("db.password", "");
        this.driverClass = trimToNull(props.getProperty("db.driver"));

        if (jdbcUrl == null) throw new IllegalArgumentException("Missing property 'db.url'");
        if (driverClass == null) throw new IllegalArgumentException("Missing property 'db.driver'");

        // Explicit driver load for plain JDBC
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("JDBC driver class not found: " + driverClass, e);
        }

        this.poolSize = parseInt(props.getProperty("db.hikari.maximum-pool-size"), 10, 1, 10_000);
        this.minimumIdle = Math.min(parseInt(props.getProperty("db.hikari.minimum-idle"), 2, 0, poolSize), poolSize);
        this.connectionTimeoutMs = parseLong(props.getProperty("db.hikari.connection-timeout"), 30_000L, 1_000L, 600_000L);
        this.idleTimeoutMs = parseLong(props.getProperty("db.hikari.idle-timeout"), 600_000L, 30_000L, 7_200_000L);
        this.maxLifetimeMs = parseLong(props.getProperty("db.hikari.max-lifetime"), 1_800_000L, 60_000L, 86_400_000L);
        this.poolName = props.getProperty("db.hikari.pool-name", "ConnectionPool");
        this.testQuery = props.getProperty("db.hikari.connection-test-query", "SELECT 1");

        logger.info(() -> String.format(
                "[%s] Initializing (url=%s, max=%d, minIdle=%d, connTimeout=%dms, idleTimeout=%dms, maxLifetime=%dms)",
                poolName, jdbcUrl, poolSize, minimumIdle, connectionTimeoutMs, idleTimeoutMs, maxLifetimeMs
        ));

        initializeConnections();
    }

    /**
     * Get the singleton instance of the ConnectionPool.
     *
     * @param props configuration properties
     * @return the singleton instance of the ConnectionPool
     * @throws SQLException if there is an error initializing the connection pool
     */
    public static synchronized ConnectionPool getInstance(Properties props) throws SQLException {
        if (instance == null) {
            instance = new ConnectionPool(props);
        }
        return instance;
    }

    // ------------------------------------------------------------
    // Core operations
    // ------------------------------------------------------------

    /**
     * Initializes the connection pool by creating the specified number of connections.
     * @throws SQLException if there is an error creating connections
     */
    private void initializeConnections() throws SQLException {
        synchronized (this) {
            while (totalConnectionsUnsafe() < minimumIdle && totalConnectionsUnsafe() < poolSize) {
                Connection c = createConnection();
                availableConnections.addLast(c);
                long now = clock.millis();
                birthTimes.put(c, now);
                lastReleasedTimes.put(c, now);
            }
            logger.info(() -> String.format("[%s] Initialized with %d idle connections (minIdle=%d).",
                    poolName, availableConnections.size(), minimumIdle));
        }
    }

    /**
     * Creates a new database connection using the configured parameters.
     * @return a new database connection
     * @throws SQLException if there is an error creating the connection
     */
    private Connection createConnection() throws SQLException {
        Connection c = DriverManager.getConnection(jdbcUrl, username, password);
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Created new physical connection");
        }
        return c;
    }

    /**
     * Acquires a connection from the pool. If no connections are available and the
     * pool has not reached its maximum size, a new connection will be created. If the
     * pool has reached its maximum size, this call will wait up to connectionTimeoutMs.
     *
     * @return a database connection from the pool
     * @throws SQLException if no connection can be obtained within the timeout or if validation fails
     */
    public synchronized Connection getConnection() throws SQLException {
        ensureOpen();

        final long deadline = clock.millis() + connectionTimeoutMs;

        while (true) {
            // 1) Reap expired/idle connections before allocating
            retireExpiredIdleUnsafe();

            // 2) Hand out from available
            while (!availableConnections.isEmpty()) {
                Connection c = availableConnections.removeFirst();

                if (!validateConnection(c)) {
                    closeQuietly(c);
                    removeTrackingUnsafe(c);
                    continue;
                }
                usedConnections.add(c);
                return c;
            }

            // 3) Create new if we are under max size
            if (totalConnectionsUnsafe() < poolSize) {
                Connection c = createConnection();
                usedConnections.add(c);
                long now = clock.millis();
                birthTimes.put(c, now);
                lastReleasedTimes.put(c, now);
                return c;
            }

            // 4) Wait for a release or until timeout
            long now = clock.millis();
            long remaining = deadline - now;
            if (remaining <= 0) {
                throw new SQLException("Timeout waiting for connection from pool '" + poolName + "'");
            }
            try {
                wait(Math.min(remaining, 1_000));
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new SQLException("Interrupted while waiting for a connection", ie);
            }
        }
    }

    /**
     * Releases a connection back to the pool. The connection is moved from the used
     * list to the available list. If the connection is null or unknown to this pool,
     * it will be safely closed.
     *
     * @param connection the database connection to be released back to the pool
     */
    public synchronized void releaseConnection(Connection connection) {
        if (connection == null) return;

        // If it doesn't belong to this pool, close it to avoid leaks.
        if (!usedConnections.remove(connection)) {
            if (!availableConnections.remove(connection)) {
                closeQuietly(connection);
                removeTrackingUnsafe(connection);
                return;
            }
        }

        // Retire if too old or too idle
        if (shouldRetireUnsafe(connection)) {
            closeQuietly(connection);
            removeTrackingUnsafe(connection);
        } else {
            availableConnections.addLast(connection);
            lastReleasedTimes.put(connection, clock.millis());
        }

        notifyAll();
    }

    /**
     * Execute test query to validate a connection. This method can be used to check
     * if a connection is valid and can successfully communicate with the database.
     * The query is a simple query (e.g., "SELECT 1") and returns true if the query
     * executes successfully, indicating that the connection is valid.
     */
    public boolean validateConnection(Connection connection) {
        if (connection == null) return false;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(this.testQuery);
            return true;
        } catch (Exception e) {
            logger.warning("Connection validation failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Closes the pool and all physical connections.
     */
    public synchronized void close() {
        if (closed) return;
        closed = true;

        // Close available
        for (Connection c : availableConnections) {
            closeQuietly(c);
        }
        availableConnections.clear();

        // Close in-use (callers should stop using them)
        for (Connection c : usedConnections) {
            closeQuietly(c);
        }
        usedConnections.clear();

        birthTimes.clear();
        lastReleasedTimes.clear();

        notifyAll();
        logger.info(() -> String.format("[%s] Pool closed.", poolName));
    }

    // ------------------------------------------------------------
    // Monitoring helpers
    // ------------------------------------------------------------

    /** Print all pool statistics to the logger. */
    public synchronized void printStats() {
        logger.info(() -> String.format(
                "[%s] stats: total=%d, used=%d, idle=%d, max=%d",
                poolName, totalConnectionsUnsafe(), usedConnections.size(),
                availableConnections.size(), poolSize
        ));
    }

    public synchronized int getIdleCount() { return availableConnections.size(); }
    public synchronized int getUsedCount() { return usedConnections.size(); }
    public synchronized int getMaxPoolSize() { return poolSize; }

    // ------------------------------------------------------------
    // Internal helpers
    // ------------------------------------------------------------

    private void ensureOpen() throws SQLException {
        if (closed) throw new SQLException("Connection pool is closed");
    }

    private int totalConnectionsUnsafe() {
        return availableConnections.size() + usedConnections.size();
    }

    private boolean shouldRetireUnsafe(Connection c) {
        long now = clock.millis();
        Long born = birthTimes.get(c);
        Long last = lastReleasedTimes.get(c);

        if (born != null && maxLifetimeMs > 0 && (now - born) > maxLifetimeMs) {
            return true;
        }

        if (last != null && idleTimeoutMs > 0 && (now - last) > idleTimeoutMs) {
            // keep at least minimum idle in pool
            if (availableConnections.size() >= Math.max(0, minimumIdle)) {
                return true;
            }
        }
        return false;
    }

    /** Reap expired/idle connections from the available queue. */
    private void retireExpiredIdleUnsafe() {
        if (availableConnections.isEmpty()) return;

        int n = availableConnections.size();
        for (int i = 0; i < n; i++) {
            Connection c = availableConnections.pollFirst();
            if (c == null) break;

            if (shouldRetireUnsafe(c) || !validateConnection(c)) {
                closeQuietly(c);
                removeTrackingUnsafe(c);
            } else {
                // still healthy; keep it
                availableConnections.addLast(c);
            }
        }

        // Refill towards minimumIdle if possible
        while (totalConnectionsUnsafe() < poolSize && availableConnections.size() < minimumIdle) {
            try {
                Connection c = createConnection();
                availableConnections.addLast(c);
                long now = clock.millis();
                birthTimes.put(c, now);
                lastReleasedTimes.put(c, now);
            } catch (SQLException e) {
                logger.warning("Failed to create connection during refill: " + e.getMessage());
                break; // don't spin forever on failure
            }
        }
    }

    private void removeTrackingUnsafe(Connection c) {
        birthTimes.remove(c);
        lastReleasedTimes.remove(c);
    }

    private static void closeQuietly(Connection c) {
        if (c == null) return;
        try {
            c.close();
        } catch (Exception ignored) { }
    }

    private static String trimToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private static int parseInt(String s, int def, int min, int max) {
        try {
            int v = Integer.parseInt(s.trim());
            if (v < min) return min;
            if (v > max) return max;
            return v;
        } catch (Exception e) {
            return def;
        }
    }

    private static long parseLong(String s, long def, long min, long max) {
        try {
            long v = Long.parseLong(s.trim());
            if (v < min) return min;
            if (v > max) return max;
            return v;
        } catch (Exception e) {
            return def;
        }
    }

    // ------------------------------------------------------------
    // Example utility for checking keys (optional)
    // ------------------------------------------------------------
    /** Returns true if the provided string looks like a sensitive key (for masking in logs). */
    @SuppressWarnings("unused")
    private static boolean isSensitiveKey(String key) {
        if (key == null) return false;
        String k = key.toLowerCase(Locale.ROOT);
        if (k.contains("password") || k.contains("secret") || k.contains("token")) return true;
        return k.endsWith(".key");
    }
}