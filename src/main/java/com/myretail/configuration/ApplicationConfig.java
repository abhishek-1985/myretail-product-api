package com.myretail.configuration;


import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;


/***
 * Configuration object defining the settings for the Spring JPA beans
 *
 */
@Configuration
@EnableMongoRepositories(basePackages={"com.myretail.repository"})
@EnableTransactionManagement

public class ApplicationConfig {

    @Bean
    public RestTemplate restTemplateBean() {return new RestTemplate();}

    // MappingMongoConverter adds a custom key type "_class" whenever a document is updated or inserted. This is to help the developer understand what class to instantiate
    // MongoDBFactory and MongoTemplate beans created to avoid _class custom key type getting added by SpringData while updating mongodb
    // To remove this extra “_class“, override the MappingMongoConverter, and passing a new DefaultMongoTypeMapper(null)

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new Mongo(), "pricingdb");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {

        //remove _class
        MappingMongoConverter converter =
                new MappingMongoConverter(mongoDbFactory(), new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(), converter);

        return mongoTemplate;

    }

}