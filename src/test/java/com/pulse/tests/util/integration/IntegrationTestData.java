package com.pulse.tests.util.integration;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Integration test fixtures for DB checks.
 */
public final class IntegrationTestData {
    private IntegrationTestData() {}

    public static final String EPOK_SCHEMA = "epok";
    public static final String EPOK_COURSE = "course";
    public static final String EPOK_MODULE = "module";

    public static final String STUDENTITS_SCHEMA = "studentits";
    public static final String STUDENTITS_STUDENT_ACCOUNT = "student_account";

    public static final String LADOK_SCHEMA = "ladok";
    public static final String LADOK_RESULT = "result";

    public static final List<Arguments> TABLES = List.of(
            arguments(EPOK_SCHEMA, EPOK_COURSE),
            arguments(EPOK_SCHEMA, EPOK_MODULE),
            arguments(STUDENTITS_SCHEMA, STUDENTITS_STUDENT_ACCOUNT),
            arguments(LADOK_SCHEMA, LADOK_RESULT)
    );

    public static Stream<Arguments> tableProvider() {
        return TABLES.stream();
    }
}
