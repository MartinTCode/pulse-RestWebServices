package com.pulse.config;

import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    
    private static final Properties props = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getResourceAsStream("/db.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find db.properties");

            }
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load database configuration", e);

        }
    }

    public static String getUrl() {
        return props.getProperty("db.url");
    }
    public static String getUser() {
        return props.getProperty("db.user");
    }
    public static String getPassword() {
        return props.getProperty("db.password");
    }
}
