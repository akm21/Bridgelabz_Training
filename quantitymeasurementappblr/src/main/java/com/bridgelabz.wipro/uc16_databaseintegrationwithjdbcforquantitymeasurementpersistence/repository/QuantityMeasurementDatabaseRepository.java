package com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.repository;

import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.entity.QuantityMeasurementEntity;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.exception.DatabaseException;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.util.ConnectionPool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

    // Logger
    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementDatabaseRepository.class.getName());

    // Singleton instance
    private static QuantityMeasurementDatabaseRepository instance;

    // SQL statements
    private static final String INSERT_QUERY =
            "INSERT INTO quantity_measurement_entity " +
                    "(" +
                    " this_value, this_unit, this_measurement_type," +
                    " that_value, that_unit, that_measurement_type," +
                    " operation," +
                    " result_value, result_unit, result_measurement_type, result_string," +
                    " is_error, error_message" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_QUERY =
            "SELECT * FROM quantity_measurement_entity ORDER BY created_at DESC";

    private static final String SELECT_BY_OPERATION =
            "SELECT * FROM quantity_measurement_entity WHERE operation = ? ORDER BY created_at DESC";

    private static final String SELECT_BY_MEASUREMENT_TYPE =
            // Filtering on the measurement type captured on the "this" side as per screenshots
            "SELECT * FROM quantity_measurement_entity " +
                    "WHERE this_measurement_type = ? ORDER BY created_at DESC";

    private static final String DELETE_ALL_QUERY =
            "DELETE FROM quantity_measurement_entity";

    private static final String COUNT_QUERY =
            "SELECT COUNT(*) FROM quantity_measurement_entity";

    private final ConnectionPool connectionPool;

    private QuantityMeasurementDatabaseRepository() {
        try {
            Properties props = loadProperties();
            this.connectionPool = ConnectionPool.getInstance(props);
            initializeDatabase();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to initialize repository/connection pool", e);
        }
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream is = QuantityMeasurementDatabaseRepository.class
                .getClassLoader().getResourceAsStream("application.properties")) {
            if (is != null) {
                props.load(is);
            } else {
                logger.warning("application.properties not found on classpath; using defaults");
            }
        } catch (IOException e) {
            logger.warning("Failed to load application.properties: " + e.getMessage());
        }
        return props;
    }

    /**
     * Initializes DB schema by executing db/schema.sql from the classpath.
     */
    private void initializeDatabase() {
        String sql = loadSchemaSql();
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            // Split on semicolons and execute each statement individually
            for (String statement : sql.split(";")) {
                String trimmed = statement.trim();
                if (!trimmed.isEmpty() && !trimmed.startsWith("--")) {
                    stmt.execute(trimmed);
                }
            }
            logger.info("Schema initialized successfully from db/schema.sql");
        } catch (SQLException e) {
            logger.warning("Schema initialization failed: " + e.getMessage());
            throw new DatabaseException("Schema initialization failed", e);
        } finally {
            closeResources(null, stmt, conn);
        }
    }

    private static String loadSchemaSql() {
        try (InputStream is = QuantityMeasurementDatabaseRepository.class
                .getClassLoader().getResourceAsStream("db/schema.sql")) {
            if (is == null) {
                throw new DatabaseException("db/schema.sql not found on classpath", null);
            }
            return new String(is.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DatabaseException("Failed to read db/schema.sql", e);
        }
    }

    public static synchronized QuantityMeasurementDatabaseRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementDatabaseRepository();
        }
        return instance;
    }

    // ------------------------------------------------------------
    // IQuantityMeasurementRepository implementations
    // ------------------------------------------------------------

    @Override
    public List<QuantityMeasurementEntity> findAll() {
        return getAllMeasurements();
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = connectionPool.getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);

            int i = 1;
            ps.setDouble(i++, entity.getThisValue());
            ps.setString(i++, entity.getThisUnit());
            ps.setString(i++, entity.getThisMeasurementType());
            ps.setDouble(i++, entity.getThatValue());
            ps.setString(i++, entity.getThatUnit());
            ps.setString(i++, entity.getThatMeasurementType());
            ps.setString(i++, entity.getOperation());
            ps.setDouble(i++, entity.getResultValue());
            ps.setString(i++, entity.getResultUnit());
            ps.setString(i++, entity.getResultType());
            ps.setString(i++, entity.getResultString());
            ps.setBoolean(i++, entity.isError());
            ps.setString(i, entity.getErrorMessage());

            ps.executeUpdate();
            logger.info("Saved QuantityMeasurementEntity to DB");
        } catch (SQLException e) {
            throw new DatabaseException("Failed to save measurement", e);
        } finally {
            closeResources(ps, conn);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        List<QuantityMeasurementEntity> results = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SELECT_ALL_QUERY);

            while (rs.next()) {
                results.add(mapResultSetToEntity(rs));
            }
            logger.info("Fetched " + results.size() + " measurements");
            return results;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch all measurements", e);
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        List<QuantityMeasurementEntity> results = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = connectionPool.getConnection();
            ps = conn.prepareStatement(SELECT_BY_OPERATION);
            ps.setString(1, operation);
            rs = ps.executeQuery();

            while (rs.next()) {
                results.add(mapResultSetToEntity(rs));
            }
            return results;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch measurements by operation: " + operation, e);
        } finally {
            closeResources(rs, ps, conn);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
        List<QuantityMeasurementEntity> results = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = connectionPool.getConnection();
            ps = conn.prepareStatement(SELECT_BY_MEASUREMENT_TYPE);
            ps.setString(1, measurementType);
            rs = ps.executeQuery();

            while (rs.next()) {
                results.add(mapResultSetToEntity(rs));
            }
            return results;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch measurements by type: " + measurementType, e);
        } finally {
            closeResources(rs, ps, conn);
        }
    }

    @Override
    public int getTotalCount() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(COUNT_QUERY);
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to count measurements", e);
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    @Override
    public void deleteAll() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = connectionPool.getConnection();
            stmt = conn.createStatement();
            int affected = stmt.executeUpdate(DELETE_ALL_QUERY);
            logger.info("Deleted rows from quantity_measurement_entity: " + affected);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete all measurements", e);
        } finally {
            closeResources(stmt, conn);
        }
    }

    @Override
    public String getPoolStatistics() {
        if (connectionPool == null) return "Pool not initialized";
        return "Pool stats -> available=" + connectionPool.getIdleCount()
                + ", used=" + connectionPool.getUsedCount()
                + ", total=" + (connectionPool.getIdleCount() + connectionPool.getUsedCount())
                + ", max=" + connectionPool.getMaxPoolSize();
    }

    @Override
    public void releaseResources() {
        if (connectionPool != null) {
            connectionPool.close();
        }
    }

    // ------------------------------------------------------------
    // Helpers
    // ------------------------------------------------------------

    private QuantityMeasurementEntity mapResultSetToEntity(ResultSet rs) throws SQLException {
        QuantityMeasurementEntity e = new QuantityMeasurementEntity();

        // These setters/fields are named to match your screenshots; adjust if your entity differs.
        e.setId(rs.getLong("id"));
        e.setThisValue(rs.getDouble("this_value"));
        e.setThisUnit(rs.getString("this_unit"));
        e.setThisMeasurementType(rs.getString("this_measurement_type"));
        e.setThatValue(rs.getDouble("that_value"));
        e.setThatUnit(rs.getString("that_unit"));
        e.setThatMeasurementType(rs.getString("that_measurement_type"));
        e.setOperation(rs.getString("operation"));
        e.setResultValue(rs.getDouble("result_value"));
        e.setResultUnit(rs.getString("result_unit"));
        e.setResultType(rs.getString("result_measurement_type"));
        e.setResultString(rs.getString("result_string"));
        e.setError(rs.getBoolean("is_error"));
        e.setErrorMessage(rs.getString("error_message"));

        Timestamp cAt = rs.getTimestamp("created_at");
        Timestamp uAt = rs.getTimestamp("updated_at");
        if (cAt != null) e.setCreatedAt(cAt.toInstant());
        if (uAt != null) e.setUpdatedAt(uAt.toInstant());

        return e;
    }

    private void closeResources(ResultSet rs, Statement stmt, Connection conn) {
        // Close in reverse order and return connection to pool
        try {
            if (rs != null) rs.close();
        } catch (SQLException ex) {
            logger.warning("Failed to close ResultSet: " + ex.getMessage());
        }
        closeResources(stmt, conn);
    }

    private void closeResources(Statement stmt, Connection conn) {
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException ex) {
            logger.warning("Failed to close Statement: " + ex.getMessage());
        }
        if (conn != null) {
            connectionPool.releaseConnection(conn);
        }
    }
}
