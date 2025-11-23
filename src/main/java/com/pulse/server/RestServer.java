package com.pulse.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import org.flywaydb.core.Flyway;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class RestServer {

    private static final String BASE_URI = "http://localhost:8080/api/";

    /**
     * Run Flyway migrations before starting the HTTP server.
     */
    private static void migrateDatabase() {
        Properties props = loadFlywayConfig();
        Flyway flyway = createFlyway(props);
        flyway.migrate();
        System.out.println("Flyway database migration completed.");
    }



    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages(
            "com.pulse");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

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

    private static Flyway createFlyway(Properties props) {
    return Flyway.configure()
            .configuration(props)
            .load();
}


}