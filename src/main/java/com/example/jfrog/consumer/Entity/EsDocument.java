package com.example.jfrog.consumer.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EsDocument {

    @JsonProperty("timeStamp")
    private Long timeStamp;

    @JsonProperty("usedSpace")
    private Long usedStorage;

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getUsedStorage() {
        return usedStorage;
    }

    public void setUsedStorage(long usedStorage) {
        this.usedStorage = usedStorage;
    }
}
