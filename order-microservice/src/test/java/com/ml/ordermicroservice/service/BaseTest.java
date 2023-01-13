package com.ml.ordermicroservice.service;

import org.testcontainers.containers.PostgreSQLContainer;

public class BaseTest {

    static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:13.2-alpine")
            .withDatabaseName("orderdbtest")
            .withUsername("postgres")
            .withInitScript("test-data.sql")
            .withPassword("postgres");

    static {
        postgreSQLContainer.start();
    }
}
