package com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ApplicationConfig {
    private static final Logger logger = Logger.getLogger(ApplicationConfig.class.getName());

    private static ApplicationConfig instance;
    private Properties properties;
    private Environment environment;

    public enum Environment {
        DEVELOPMENT,
        TESTING,
        PRODUCTION
    }
    public enum ConfigKey {
        REPOSITORY_TYPE("repository.type"),
        DB_DRIVER_CLASS("db.driver"),
        DB_URL("db.url"),
        DB_USERNAME("db.username"),
        DB_PASSWORD("db.password"),
        DB_POOL_SIZE("db.pool.size"),
        HIKARI_MAX_POOL_SIZE("db.hikari.maximum-pool-size"),
        HIKARI_MIN_IDLE("db.hikari.minimum-idle"),
        HIKARI_IDLE_TIMEOUT("db.hikari.idle-timeout"),
        HIKARI_CONNECTION_TIMEOUT("db.hikari.connection-timeout"),
        HIKARI_MAX_LIFETIME("db.hikari.max-lifetime"),
        HIKARI_POOL_NAME("db.hikari.pool-name"),
        HIKARI_CONNECTION_TEST_QUERY("db.hikari.connection-test-query");

        private final String key;

        ConfigKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

        private ApplicationConfig(){
            loadConfigurations();
        }

        public static synchronized ApplicationConfig getInstance() {
            if (instance == null) {
                instance = new ApplicationConfig();
            }
            return instance;
        }

        private void loadConfigurations(){
            properties = new Properties();
            try{
                String env = System.getProperty("app.env");
                if(env == null || env.isEmpty()){
                    env = System.getenv("APP_ENV");
                }

                String configFile = "application.properties";
                InputStream input = ApplicationConfig.class
                        .getClassLoader()
                        .getResourceAsStream(configFile);
                if(input != null){
                    properties.load(input);
                    logger.info("Loaded configuration from " + configFile);
                   if(env == null || env.isEmpty()){
                        env = properties.getProperty("app.env", "development");
                    }
                   this.environment=Environment.valueOf(env.toUpperCase());
                }else {
                    logger.warning("Configuration file not found, using defaults");
                    loadDefaults();
                }
            } catch (Exception e) {
                logger.severe("Error loading configuration: " + e.getMessage());
                loadDefaults();
            }
        }

        private void loadDefaults() {
            if (properties == null) {
                properties = new java.util.Properties();
            }

            // --- Environment defaults ---
            properties.setProperty("app.env", "development");
            try {
                this.environment = Environment.valueOf("DEVELOPMENT");
            } catch (IllegalArgumentException iae) {
                // Fallback if enum changes
                logger.warning("Environment enum does not contain DEVELOPMENT; defaulting to first constant.");
                try {
                    this.environment = Environment.values()[0];
                } catch (Exception ignored) {
                    // ignore, leave environment as-is if not available
                }
            }

            // --- H2 Database defaults (local file DB, auto server for multi-process access) ---
            properties.setProperty("db.driver", "org.h2.Driver");
            properties.setProperty("db.url", "jdbc:h2:./quantitymeasurementdb");
            properties.setProperty("db.username", "sa");
            properties.setProperty("db.password", "");

            // --- HikariCP defaults (conservative and production-friendly timeouts) ---
            properties.setProperty("db.hikari.maximum-pool-size", "10");
            properties.setProperty("db.hikari.minimum-idle", "2");
            properties.setProperty("db.hikari.connection-timeout", "30000");  // 30s: wait for a connection
            properties.setProperty("db.hikari.idle-timeout", "600000");       // 10m: retire idle connections
            properties.setProperty("db.hikari.max-lifetime", "1800000");      // 30m: retire connections before DB kills them
            properties.setProperty("db.hikari.pool-name", "QuantityMeasurementPool");
            properties.setProperty("db.hikari.connection-test-query", "SELECT 1");

            logger.info("Default configuration loaded (H2 + HikariCP) for 'development' environment.");
        }

        public String getProperty(String key) {
            return properties.getProperty(key);
        }

        public String getProperty(String key,String defaultValue) {
            return properties.getProperty(key,defaultValue);
        }

        public int getIntProperty(String key,int defaultValue) {
            try {
                return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }

        public String getEnvironment() {
            return environment.name();
        }

        public boolean isConfigKey(String key){
            for(ConfigKey configKey : ConfigKey.values()){
                if(configKey.getKey().equals(key)){
                    return true;
                }
            }
            return false;
        }
        public void printAllProperties(){
            if (properties == null || properties.isEmpty()) {
                logger.info("No configuration properties are loaded.");
                return;
            }

            // Collect and sort keys for stable logging
            java.util.List<String> keys = new java.util.ArrayList<>();
            for (Object k : properties.keySet()) {
                if (k != null) {
                    keys.add(String.valueOf(k));
                }
            }
            java.util.Collections.sort(keys);

            logger.info("---- Begin Configuration Properties ----");

            for (String key : keys) {
                // Skip some noisy/irrelevant keys if present (adjust as needed)
                if (key.startsWith("java.") || key.startsWith("sun.") || key.startsWith("user.") ||
                        key.startsWith("awt.") || key.startsWith("line.") || key.startsWith("file.")) {
                    continue;
                }

                String value = properties.getProperty(key);

                // Mask sensitive values
                if (isSensitiveKey(key)) {
                    value = "******";
                }

                logger.info(key + " = " + value);
            }

            logger.info("---- End Configuration Properties ----");
        }

    /** Heuristic to determine if a key is sensitive and should be masked. */
    private boolean isSensitiveKey(String key) {
        if (key == null) return false;
        String k = key.toLowerCase(java.util.Locale.ROOT);

        // Obvious sensitive terms
        if (k.contains("password") || k.contains("secret") || k.contains("token")) {
            return true;
        }

        // Keys named "...key" are often secrets, but avoid masking common non-secret keys.
        if (k.endsWith(".key") || k.contains("_key")) {
            // Avoid false positives for visible names, pool-name, etc.
            if (k.contains("pool-name") || k.endsWith("name")) {
                return false;
            }
            return true;
        }
        return false;
    }
}
