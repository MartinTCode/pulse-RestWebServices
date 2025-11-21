package com.pulse.server;

import org.flywaydb.core.Flyway;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.pulse.config.DatabaseConfig;

import java.net.URI;

public class RestServer {

    private static final String BASE_URI = "http://localhost:8080/api/";

    /**
     * Run Flyway migrations before starting the HTTP server.
     */
    private static void migrateDatabase() {
        Flyway flyway = Flyway.configure()
                .dataSource(DatabaseConfig.getUrl(), DatabaseConfig.getUser(), DatabaseConfig.getPassword())
                .load();
        flyway.migrate();
        System.out.println("Flyway database migration completed.");
    }

    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages(
            "com.pulse.epok.api",
            "com.pulse.studentits.api",
            "com.pulse.ladok.api");
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
}