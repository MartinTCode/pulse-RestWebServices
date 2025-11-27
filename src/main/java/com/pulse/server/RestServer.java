package com.pulse.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import org.flywaydb.core.Flyway;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * REST server for the Pulse application using Jersey and Grizzly HTTP server.
 * Also handles Flyway database migrations on startup.
 */
public class RestServer {

    private static final String BASE_URI = "http://localhost:8080/api/";

    /**
     * Runs Flyway migrations before starting the HTTP server.
     */
    private static void migrateDatabase() {
        Properties props = loadFlywayConfig();
        Flyway flyway = createFlyway(props);
        flyway.migrate();
        System.out.println("Flyway database migration completed.");
    }

    /**
     * Starts the Grizzly HTTP server with Jersey configuration.
     * @return HttpServer instance
     * @throws IOException if server fails to start
     */
    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages(
            "com.pulse");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method to start the REST server and run database migrations.
     * @param args Command line arguments
     * @throws IOException if server fails to start
     */
    public static void main(String[] args) {
        migrateDatabase();
        final HttpServer server = startServer();

        System.out.println("Jersey + Grizzly running at: " + BASE_URI);
        System.out.println("Press Ctrl+C to stop.");

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
            System.out.println("Main thread interrupted, shutting down.");
        }
    }

    /**
     * Loads Flyway configuration from flyway.conf file in classpath.
     * @return Properties object containing Flyway configuration
     * @throws RuntimeException if loading fails
     */
    private static Properties loadFlywayConfig() {
        try (InputStream in = RestServer.class
                .getClassLoader()
                .getResourceAsStream("flyway.conf")) {

            if (in == null) {
                throw new IllegalStateException("flyway.conf not found on classpath");
            }

            Properties props = new Properties();
            props.load(in);
            return props;

        } catch (IOException e) {
            throw new RuntimeException("Failed to load flyway.conf", e);
        }
        }

    /**
     * Creates a Flyway instance with the given configuration properties.
     * @param props Properties object containing Flyway configuration
     * @return Flyway instance
     * @throws RuntimeException if Flyway creation fails
     */
    private static Flyway createFlyway(Properties props) {
        return Flyway.configure()
            .configuration(props)
            .load();
    }
}