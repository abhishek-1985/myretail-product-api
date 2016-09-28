package com.myretail.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ItemResponse {

    @JsonProperty("id")
    String id;
    @JsonProperty("name")
    String name;
    @JsonProperty("current_price")
    ItemPricing  itemPricing;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemPricing getItemPricing() {
        return itemPricing;
    }

    public void setItemPricing(ItemPricing itemPricing) {
        this.itemPricing = itemPricing;
    }

    @Override
    public String toString() {
        return "ItemResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", itemPricing=" + itemPricing +
                '}';
    }
}
