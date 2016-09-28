package com.myretail.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.model.Pricing;
import com.myretail.repository.PricingMongoRepository;
import com.myretail.response.ItemDescResponse;
import com.myretail.response.ItemPricing;
import com.myretail.response.ItemResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Service
public class ItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);
    private static final String TRACE_ENTERING = "Entering: {}";
    private static final String TRACE_EXITING = "Exiting: {}";
    private final String apiUrl;
    private final String apiSearch;
    private final String apiKey;

    @Autowired
    protected PricingMongoRepository pricingMongoRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ItemService(@Value("${api.service.url}") String apiUrlInput, @Value("${api.service.search}") String apiSearchInput,
                @Value("${api.service.key}") String apiKeyInput) {
        apiUrl = apiUrlInput;
        apiSearch = apiSearchInput;
        apiKey = apiKeyInput;
    }

    /**
     * Gets the Item Data and stores in ItemResponse
     *
     * @return itemresponse
     */

    public ItemResponse getItemData(String item_id) throws IOException {
        LOGGER.trace(TRACE_ENTERING, Thread.currentThread().getStackTrace()[1]);

        //Invoke the API url with item_id input and fetch the item General Description and store in ItemDescResponse class
        ItemDescResponse itemDescResponse = getItemGeneralDesc(item_id);

        //Extract pricing information for item_id input from Mongodb
        Pricing pricing = getPricingByItem(item_id);

        ItemPricing itemPricing =new ItemPricing();

        // Store pricing data in itemPricing class
        itemPricing.setValue(pricing.getValue());
        itemPricing.setCurrency(pricing.getCurrency_code());

        // Store Item id, name and Item Pricing in ItemResponse class
        ItemResponse itemResponse = new ItemResponse();

        itemResponse.setId(item_id);
        itemResponse.setName(itemDescResponse.getGeneralDescription());
        itemResponse.setItemPricing(itemPricing);

        LOGGER.trace(TRACE_EXITING, Thread.currentThread().getStackTrace()[1]);

        return itemResponse;
    }

    /**
     * Gets the item description from api.target.com
     *
     * @return itemrdescesponse
     */

    protected ItemDescResponse getItemGeneralDesc(String item) throws IOException{

        LOGGER.trace(TRACE_ENTERING, Thread.currentThread().getStackTrace()[1]);
        LOGGER.debug("getGeneralDesc item input: {}", item);
        LOGGER.debug("apiurl is :- " + apiUrl + item + "?" + apiSearch + "&key=" + apiKey);

        ResponseEntity<String> apiresponse= restTemplate.getForEntity(apiUrl + item + "?" + apiSearch + "&key=" + apiKey,String.class);

        LOGGER.info("Response Code(api service): {}", apiresponse.getStatusCode());
        LOGGER.debug("Response Body (api service): {}", apiresponse.getBody());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(apiresponse.getBody());

        JsonNode items = root.findValue("items");
        ItemDescResponse itemDescResponse = mapper.readValue(items.get(0).toString(),ItemDescResponse.class);

        LOGGER.debug("ItemDescResponse: {}", itemDescResponse);
        LOGGER.trace(TRACE_EXITING, Thread.currentThread().getStackTrace()[1]);

        return itemDescResponse;
    }

    /**
     * Gets the Item Pricing from Mongodb and stores in ItemResponse
     *
     * @return itempricing
     */

    protected Pricing getPricingByItem(String item) throws IOException{
        LOGGER.trace(TRACE_ENTERING, Thread.currentThread().getStackTrace()[1]);

        Pricing pricing = pricingMongoRepository.findByitem(item);

        LOGGER.debug("itemspace={} ", pricing.toString());
        LOGGER.trace(TRACE_EXITING, Thread.currentThread().getStackTrace()[1]);

        return pricing;
    }

    /**
     * Updates the pricing in mongo database.
     *
     * @param - itempricing to be updated
     *
     * @return - updated ItemResponse entity with updated pricing
     */

    public ItemResponse update(ItemResponse itemJson) throws IOException{

        LOGGER.trace(TRACE_ENTERING, Thread.currentThread().getStackTrace()[1]);
        LOGGER.info("Update pricing with itemid = {} ",itemJson.getId());


        Pricing existingpricing = pricingMongoRepository.findByitem(itemJson.getId());

        LOGGER.debug("Read pricing with itemid = {} ",existingpricing.getItem());


        existingpricing.setValue(itemJson.getItemPricing().getValue());
        existingpricing.setCurrency_code(itemJson.getItemPricing().getCurrency());

        LOGGER.debug("pricing with updated values \n",existingpricing.toString());

        Pricing updatedPricing = pricingMongoRepository.save((existingpricing));

        LOGGER.debug("pricing updated successfully");

        // updatedItemPricing object updated with new value and currency
        ItemPricing updatedItemPricing = new  ItemPricing();
        updatedItemPricing.setValue(updatedPricing.getValue());
        updatedItemPricing.setCurrency(updatedPricing.getCurrency_code());

        // updatedItem object updated with input id, name and updatedItemPricing
        ItemResponse updatedItem = new ItemResponse();
        updatedItem.setId(updatedPricing.getItem());
        updatedItem.setName(itemJson.getName());
        updatedItem.setItemPricing(updatedItemPricing);

        return updatedItem;

    }

}
