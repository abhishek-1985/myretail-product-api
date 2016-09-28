package com.myretail.controller;

import com.myretail.model.Pricing;
import com.myretail.response.ItemResponse;
import com.myretail.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/products")

public class ItemController {

    @Autowired
    ItemService itemService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    private static final String TRACE_ENTERING = "Entering: {}";
    private static final String TRACE_EXITING = "Exiting: {}";

    @ResponseBody
    @RequestMapping (value = "/{id}", method = RequestMethod.GET, produces = "application/json")

    public ItemResponse getItemData(@PathVariable("id") String id) {
        LOGGER.trace(TRACE_ENTERING, Thread.currentThread().getStackTrace()[1]);
        LOGGER.info("Request received (controller) id:{}", id);

        //ResponseEntity for micresponse
        ItemResponse itemResponse = null;
        try {
            // Invoke the Item Service
            itemResponse = itemService.getItemData(id);
        }
        catch(Exception ex){
            LOGGER.error(ex.getMessage(),ex);

            throw new ItemNotFoundException();
        }

        LOGGER.info("Response (controller):{}", itemResponse);
        LOGGER.trace(TRACE_EXITING, Thread.currentThread().getStackTrace()[1]);

        return itemResponse;
    }


    /**
     * Return Updated Pricing
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody

    public ItemResponse updateItemPricing(@PathVariable("id") String id, @RequestBody ItemResponse item) throws Exception {

        LOGGER.trace(TRACE_ENTERING, Thread.currentThread().getStackTrace()[1]);
        LOGGER.info("Updating Pricing: {}", item.getId());

        LOGGER.trace(TRACE_EXITING, Thread.currentThread().getStackTrace()[1]);

        if(id.equals(item.getId())){
            return itemService.update(item);

        } else {
            throw new IdDoesnotMatchException();
        }
    }

    // Custom Runtime exception class for ItemController
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "item data not found")  // 404
    protected class ItemNotFoundException extends RuntimeException {
        // ...
    }

    // Custom Runtime exception class for ItemController
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED, reason = "Path Variable does not match ID in Request Body")  // 404
    protected class IdDoesnotMatchException extends RuntimeException {
        // ...
    }
}
