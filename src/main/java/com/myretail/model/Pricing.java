package com.myretail.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pricing")

public class Pricing {

    @Id
    private String id;

    private String item;

    private Float value;

    private String currency_code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }


    @Override
    public String toString() {
        return "Pricing{" +
                "id='" + id + '\'' +
                ", item='" + item + '\'' +
                ", value=" + value +
                ", currency_code='" + currency_code + '\'' +
                '}';
    }
}
