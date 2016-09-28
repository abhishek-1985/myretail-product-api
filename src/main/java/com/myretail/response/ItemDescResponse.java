package com.myretail.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDescResponse {


    @JsonProperty (value="general_description", required=true)
    protected String generalDescription;

    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setGeneralDescription(String generalDescription) {
        this.generalDescription = generalDescription;
    }

    @Override
    public String toString() {
        return "ItemDescResponse{" +
                "generalDescription='" + generalDescription + '\'' +
                '}';
    }
}
