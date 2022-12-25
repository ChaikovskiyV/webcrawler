package com.chaikouski.webcrawler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * @author Viktar Chaikouski
 * <p>
 * The type TestConfig.
 * <p>
 * Includes the configuration for tests.
 */
@Configuration
@PropertySource("classpath:/application-test.properties")
public class TestConfig {
    private static final String TEST_SCHEMA = "classpath:base/testschema.sql";
    private static final String TEST_DATA = "classpath:base/testdata.sql";

    /**
     * Returns dataSource bean.
     *
     * @return the DataSource bean
     */
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScripts(TEST_SCHEMA)
                .addScripts(TEST_DATA)
                .build();
    }

    /**
     * Returns JdbcTemplate bean.
     *
     * @return the JdbcTemplate bean
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}