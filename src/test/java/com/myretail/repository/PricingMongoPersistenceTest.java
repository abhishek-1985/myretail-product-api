package com.myretail.repository;

import com.myretail.Application;
import com.myretail.model.Pricing;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;



import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ComponentScan
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class PricingMongoPersistenceTest {

    @Autowired
    private PricingMongoRepository pricingMongoRepository;

    //JUnit for  Item data fetch against DPCI input list
    @Test
    public void testfindByitem() {

        Pricing itemresult=pricingMongoRepository.findByitem("16483589");
        assertEquals("16483589",itemresult.getItem());

    }
}
