package com.myretail.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemPricing {

    @JsonProperty("value")
    Float value;
    @JsonProperty("currency_code")
    String currency;

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "ItemPricing{" +
                "value=" + value +
                ", currency='" + currency + '\'' +
                '}';
    }
}
