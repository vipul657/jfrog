package com.example.demo.consumer.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EsDocument {

    @JsonProperty("timeStamp")
    private Long timeStamp;

    @JsonProperty("usedSpace")
    private String usedStorage;

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUsedStorage() {
        return usedStorage;
    }

    public void setUsedStorage(String usedStorage) {
        this.usedStorage = usedStorage;
    }
}
