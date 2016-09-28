package com.myretail.controller;

import com.myretail.Application;
import com.myretail.response.ItemPricing;
import com.myretail.response.ItemResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ComponentScan
@WebIntegrationTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class ItemControllerTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ItemController itemController;


    //MockServer
    private MockRestServiceServer mockRestServiceServer;

    private String API_URL = "https://api.target.com/products/v3/15117729?fields=descriptions&id_type=TCIN&key=43cJWpLjH8Z8oR18KdrZDBKAgLLQKJjz";

    private String INPUT_JSON = "{\n" +
            "  \"id\": \"16696652\",\n" +
            "  \"name\": \"Beats Solo 2 Wireless Headphone Black\",\n" +
            "  \"current_price\": {\n" +
            "    \"value\": 21.00,\n" +
            "    \"currency_code\": \"USD\"\n" +
            "  }\n" +
            "}";

    private String SUCCESS_JSON = "{\n" +
            "  \"product_composite_response\": {\n" +
            "    \"request_attributes\": [\n" +
            "      {\n" +
            "        \"name\": \"product_id\",\n" +
            "        \"value\": \"15117729\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"key\",\n" +
            "        \"value\": \"43cJWpLjH8Z8oR18KdrZDBKAgLLQKJjz\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"id_type\",\n" +
            "        \"value\": \"TCIN\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"fields\",\n" +
            "        \"value\": \"descriptions\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"items\": [\n" +
            "      {\n" +
            "        \"identifier\": [\n" +
            "          {\n" +
            "            \"id_type\": \"DPCI\",\n" +
            "            \"id\": \"057-10-3003\",\n" +
            "            \"is_primary\": null,\n" +
            "            \"source\": \"Online and Store\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"id_type\": \"TCIN\",\n" +
            "            \"id\": \"15117729\",\n" +
            "            \"is_primary\": null,\n" +
            "            \"source\": \"Online\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"relation\": \"VC\",\n" +
            "        \"relation_description\": \"Variation Child\",\n" +
            "        \"data_page_link\": \"http://www.target.com/p/apple-ipad-air-2-wi-fi/-/A-51731130\",\n" +
            "        \"imn_identifier\": 13507904,\n" +
            "        \"parent_id\": \"51731130\",\n" +
            "        \"is_orderable\": true,\n" +
            "        \"is_sellable\": true,\n" +
            "        \"general_description\": \"Apple iPad Air 2 Wi-Fi 16GB, Gold\",\n" +
            "        \"is_circular_publish\": true,\n" +
            "        \"business_process_status\": [\n" +
            "          {\n" +
            "            \"process_status\": {\n" +
            "              \"is_ready\": true,\n" +
            "              \"operation_description\": \"assortment ready\",\n" +
            "              \"operation_code\": \"PAAP\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"process_status\": {\n" +
            "              \"is_ready\": false,\n" +
            "              \"operation_description\": \"import ready\",\n" +
            "              \"operation_code\": \"PIPT\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"process_status\": {\n" +
            "              \"is_ready\": true,\n" +
            "              \"operation_description\": \"order ready\",\n" +
            "              \"operation_code\": \"PORD\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"process_status\": {\n" +
            "              \"is_ready\": true,\n" +
            "              \"operation_description\": \"presentation ready\",\n" +
            "              \"operation_code\": \"PPRS\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"process_status\": {\n" +
            "              \"is_ready\": true,\n" +
            "              \"operation_description\": \"project ready\",\n" +
            "              \"operation_code\": \"PCMT\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"process_status\": {\n" +
            "              \"is_ready\": true,\n" +
            "              \"operation_description\": \"replenishment ready\",\n" +
            "              \"operation_code\": \"PRPL\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"process_status\": {\n" +
            "              \"is_ready\": false,\n" +
            "              \"operation_description\": \"scale ready\",\n" +
            "              \"operation_code\": \"PSCL\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"process_status\": {\n" +
            "              \"is_ready\": true,\n" +
            "              \"operation_description\": \"target.com ready\",\n" +
            "              \"operation_code\": \"PTGT\"\n" +
            "            }\n" +
            "          }\n" +
            "        ],\n" +
            "        \"dpci\": \"057-10-3003\",\n" +
            "        \"department_id\": 57,\n" +
            "        \"class_id\": 10,\n" +
            "        \"item_id\": 3003,\n" +
            "        \"online_description\": {\n" +
            "          \"value\": \"Apple&reg; iPad Air 2 16GB Wi-Fi - Gold\",\n" +
            "          \"type\": \"GENL\"\n" +
            "        },\n" +
            "        \"store_description\": {\n" +
            "          \"value\": \"Apple iPad Air 2 Wi-Fi 16GB, Gold\",\n" +
            "          \"type\": \"GENL\"\n" +
            "        },\n" +
            "        \"alternate_description\": [\n" +
            "          {\n" +
            "            \"type\": \"ADSG\",\n" +
            "            \"value\": \"Apple iPad Air 2\",\n" +
            "            \"type_description\": \"Ad Signage Description\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"VEND\",\n" +
            "            \"value\": \"iPad-003\",\n" +
            "            \"type_description\": \"Vendor Description\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"SHLF\",\n" +
            "            \"value\": \"AIR2 WIFI 16GB GOLD\",\n" +
            "            \"type_description\": \"Shelf Desc\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"SELP\",\n" +
            "            \"value\": \"<*Bluetooth 4.0\",\n" +
            "            \"type_description\": \"Selling points Description\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"POS\",\n" +
            "            \"value\": \"IPADAIR2GD16\",\n" +
            "            \"type_description\": \"POS Desc\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"GENL\",\n" +
            "            \"value\": \"Apple iPad Air 2 Wi-Fi 16GB, Gold\",\n" +
            "            \"type_description\": \"General Desc\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"DETL\",\n" +
            "            \"value\": \"Thinner and more lightweight than ever, the Apple iPad Air 2 takes tablet technology to a whole new level. Only 6.1mm, this ultra-thin iPad has a re-engineered 9.7&#34; Retina display with 2048 x 1536 resolution for crisp images. Features include an 8MP iSight camera, better-than-ever FaceTime HD camera, 16GB capacity, faster WiFi connectivity and a battery life of up to 10 hours. Download your favorite apps to experience all your favorite media, from streaming movies to devouring eBooks to playing music, with the ultimate portable convenience.\",\n" +
            "            \"type_description\": \"Online Detailed Description\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"features\": [\n" +
            "          {\n" +
            "            \"feature\": \"Maximum Resolution:  2048 x 1536\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"feature\": \"Display Features:  Retina Display, Backlit LED, Fingerprint-resistant, Anti-Glare Coating, Touch Screen\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"feature\": \"Computer Features:  Built-In Bluetooth, Touch Screen, Built-In Speaker, Fingerprint Reader\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"feature\": \"Processor Brand:  Apple\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"feature\": \"Processor Type:  Apple A8X\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"feature\": \"System RAM:  1024\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"feature\": \"Data Storage Capacity:  16GB Hard Drive Capacity\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"feature\": \"Wired Connectivity:  Apple Lightning Connector\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"feature\": \"Wireless Technology:  Bluetooth, Wi-Fi\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"feature\": \"Wireless Standard:  IEEE 802.11a, IEEE 802.11b, IEEE 802.11g, IEEE 802.11n, IEEE 802.11ac, Bluetooth 4.0\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"feature\": \"Audio Features:  Built-In Speakers\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"feature\": \"Battery Charge Life:  Up to 10 Hours\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"feature\": \"Operating System:  Apple iOS 8\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"feature\": \"Software Included:  iTunes\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"feature\": \"Includes:  Lightning-to-USB cable, Owner&#39;s Manual, Power Cord Adapter\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"feature\": \"Battery  required, included: lithium ion\"\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";

    @Before
    public void runBeforeTestMethod() {

        //create a mock Server instance for RestTemplate@Test
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);

    }

    @Test
    public void testgetItemData() {

        mockRestServiceServer
                .expect(requestTo(API_URL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(SUCCESS_JSON,
                                MediaType.TEXT_PLAIN));

        ItemResponse itemResponse = itemController.getItemData("15117729");
        mockRestServiceServer.verify();

        // assert the response
        assertThat(itemResponse.getId(), is("15117729"));
        assertThat(itemResponse.getName(),is("Apple iPad Air 2 Wi-Fi 16GB, Gold"));
        assertThat(itemResponse.getItemPricing().getCurrency(),is("USD"));
    }

    // Test to verify updateItemPricing method so that updated value is saved in the mongodb
    @Test
    public void testupdateItemPricing() throws Exception{

        ItemPricing inputitemPricing = new ItemPricing();
        inputitemPricing.setValue((float) 30);
        inputitemPricing.setCurrency("USD");

        ItemResponse inputItem = new ItemResponse();
        inputItem.setId("16696652");
        inputItem.setName("Beats Solo 2 Wireless Headphone Black");
        inputItem.setItemPricing(inputitemPricing);

        ItemResponse itemResponse = itemController.updateItemPricing(inputItem);

        assertNotNull(itemResponse);
        assertEquals(30.0,itemResponse.getItemPricing().getValue(),0.01);

    }

}
