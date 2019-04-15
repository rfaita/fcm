package com.product.fcm.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;
import java.util.List;

/**
 * @author rfaita
 */
@Configuration
@Profile({"default", "docker-compose"})
@EnableMongoRepositories("com.product.fcm.server.repository")
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${fcm.mongodb.hostname}")
    private String mongoHost;

    @Value("${fcm.mongodb.port}")
    private String mongoPort;

    @Value("${fcm.mongodb.database}")
    private String mongoDB;

    @Override
    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(mongoHost + ":" + mongoPort);
    }

    @Override
    protected String getDatabaseName() {
        return mongoDB;
    }

    @Override
    protected List<String> getMappingBasePackages() {
        return Arrays.asList("com.product.fcm.server.model");
    }
}
