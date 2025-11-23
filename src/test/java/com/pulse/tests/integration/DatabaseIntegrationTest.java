package com.pulse.tests.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Integration tests that verify the configured database is reachable
 * and that the expected schemas/tables exist (epok.course, studentits.student_account, ladok.result).
 *
 * These tests are defensive: if `db.properties` is missing or the DB is not
 * reachable, the tests are skipped instead of failing so they are safe to run
 * in environments without a running Postgres instance.
 */
public class DatabaseIntegrationTest {

    // holds the loaded DB connection properties
    private static Properties dbProps;

    @BeforeAll
    public static void loadProps() throws Exception {
        dbProps = new Properties();
        // try to fetch db.properties from "src/main/resources/db.properties".
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                // No configuration found on the classpath; skip DB integration tests.
                Assumptions.assumeTrue(false, "db.properties not found on classpath; skipping DB integration tests");
            }
            // Populate dbProps with keys: db.url, db.user, db.password
            dbProps.load(in);
        }
    }

    @Test
    public void testCanConnect() throws Exception {
        String url = dbProps.getProperty("db.url");
        String user = dbProps.getProperty("db.user");
        String pass = dbProps.getProperty("db.password");

        // Trying a plain JDBC connection.
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            // If the connection object is null or closed, assume the DB is
            // unavailable and skip the remainder of the tests.
            Assumptions.assumeTrue(conn != null && !conn.isClosed(), "Could not open DB connection; skipping tests");
            // Basic assertion to make intent explicit: a usable connection was returned.
            assertNotNull(conn);
        } catch (Exception e) {
            // Any exception while connecting -> skip the test with the error
            // message included for easier debugging.
            Assumptions.assumeTrue(false, "DB not reachable: " + e.getMessage());
        }
    }

    @Test
    public void testEpokCourseTableExists() throws Exception {
        String url = dbProps.getProperty("db.url");
        String user = dbProps.getProperty("db.user");
        String pass = dbProps.getProperty("db.password");

        // Connect and verify the `epok.course` table exists. A failure here
        // indicates the database schema/migrations haven't been applied.
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            Assumptions.assumeTrue(conn != null && !conn.isClosed(), "DB connection not available; skipping table existence test");

            boolean exists = tableExists(conn, "epok", "course");
            assertTrue(exists, "Expected table epok.course to exist");
        }
    }

    @Test
    public void testStudentitsStudentAccountTableExists() throws Exception {
        String url = dbProps.getProperty("db.url");
        String user = dbProps.getProperty("db.user");
        String pass = dbProps.getProperty("db.password");

        // Verify the `studentits.student_account` table exists which is part of
        // the StudentITS schema. This helps ensure that the student system's
        // baseline migration was applied.
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            Assumptions.assumeTrue(conn != null && !conn.isClosed(), "DB connection not available; skipping studentits table test");

            boolean exists = tableExists(conn, "studentits", "student_account");
            assertTrue(exists, "Expected table studentits.student_account to exist");
        }
    }

    @Test
    public void testLadokResultTableExists() throws Exception {
        String url = dbProps.getProperty("db.url");
        String user = dbProps.getProperty("db.user");
        String pass = dbProps.getProperty("db.password");

        // Verify the `ladok.result` table exists which stores results in the
        // Ladok schema. A missing table here commonly means Flyway migrations
        // were not executed for the Ladok schema.
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            Assumptions.assumeTrue(conn != null && !conn.isClosed(), "DB connection not available; skipping ladok table test");

            boolean exists = tableExists(conn, "ladok", "result");
            assertTrue(exists, "Expected table ladok.result to exist");
        }
    }

    /** Helper to check whether a table exists in a given schema. */
    private boolean tableExists(Connection conn, String schema, String tableName) throws Exception {
        // Use JDBC DatabaseMetaData.getTables to query whether the named table
        // exists in the provided schema. Note: many JDBC drivers (Postgres)
        // expect lower-case schema/table names unless quoted; the migration
        // scripts create lowercase identifiers so this call works as-is.
        DatabaseMetaData meta = conn.getMetaData();
        try (ResultSet rs = meta.getTables(null, schema, tableName, new String[]{"TABLE"})) {
            //rs.next to move to first metadata row, which will only exist if the table is found
            return rs.next();
        }
    }
}
