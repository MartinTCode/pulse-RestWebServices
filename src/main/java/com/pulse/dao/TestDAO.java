package com.pulse.dao;

import com.pulse.config.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestDAO {
    
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
