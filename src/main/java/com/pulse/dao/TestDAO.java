package com.pulse.dao;

import com.pulse.config.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Data Access Object for testing database connection
 */
public class TestDAO {
    
    /**
     * Tests the database connection by executing a simple query
     * @return The database version if connection is successful, otherwise an error message
     */
    public String testDatabase() {
        String sql = "SELECT version()";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

                return rs.next() ? rs.getString(1) : "No result";

        } catch (Exception e) {
            return "Database connection failed: " + e.getMessage();
        }
    }    
}
