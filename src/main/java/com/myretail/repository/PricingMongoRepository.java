package com.myretail.repository;

import com.myretail.model.Pricing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingMongoRepository extends MongoRepository<Pricing,String> {

     Pricing findByitem (String item);
}
