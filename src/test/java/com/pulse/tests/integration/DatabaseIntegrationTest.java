package com.pulse.tests.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

// arguments() helper no longer needed here; IntegrationTestData provides the table list
import com.pulse.tests.util.integration.IntegrationTestData;

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

    // Central list of schema/table pairs is provided by IntegrationTestData
    // (keeps integration expectations in a single place shared by tests).
    // See com.pulse.tests.util.integration.IntegrationTestData

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
    // Parameterized test to cover multiple schema.table combinations without
    // duplicating nearly identical code for each table. 
    @ParameterizedTest(name = "Check table exists: {0}.{1}") // The {0}.{1} in the gets the arguments from the tableProvider
    @MethodSource("tableProvider")
    public void testTableExists(String schema, String tableName) throws Exception {
        try (Connection conn = openConnectionOrSkip()) {
            boolean exists = tableExists(conn, schema, tableName);
            assertTrue(exists, String.format("Expected table %s.%s to exist", schema, tableName));
        }
    }


    // Provides schema/table pairs to the parameterized test above.
    private static Stream<Arguments> tableProvider() {
        // Return a fresh stream from the top-level list so the MethodSource can
        // be reused safely by JUnit.
        return IntegrationTestData.tableProvider();
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

    /**
     * Open a JDBC connection using the loaded properties. If the connection
     * cannot be opened the test is skipped (via JUnit Assumptions).
     */
    private Connection openConnectionOrSkip() throws Exception {
        String url = dbProps.getProperty("db.url");
        String user = dbProps.getProperty("db.user");
        String pass = dbProps.getProperty("db.password");
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Assumptions.assumeTrue(conn != null && !conn.isClosed(), "Could not open DB connection; skipping tests");
            return conn;
        } catch (Exception e) {
            Assumptions.assumeTrue(false, "DB not reachable: " + e.getMessage());
            return null; // unreachable but keeps compiler happy
        }
    }
}
