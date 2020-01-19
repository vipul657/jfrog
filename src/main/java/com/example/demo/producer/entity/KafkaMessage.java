package com.example.demo.producer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class KafkaMessage implements Serializable {

    @JsonProperty("timeStamp")
    private Long timeStamp;

    @JsonProperty("usedSpace")
    private String usedStorage;

    public KafkaMessage(Long timeStamp, String usedStorage) {
        this.timeStamp = timeStamp;
        this.usedStorage = usedStorage;
    }
}
