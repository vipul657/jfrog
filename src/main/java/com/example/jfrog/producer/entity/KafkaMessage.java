package com.example.jfrog.producer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class KafkaMessage implements Serializable {

    @JsonProperty("timeStamp")
    private Long timeStamp;

    @JsonProperty("usedSpace")
    private Long usedStorage;

    public KafkaMessage(Long timeStamp, Long usedStorage) {
        this.timeStamp = timeStamp;
        this.usedStorage = usedStorage;
    }
}
