package com.example.jfrog.producer.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KafkaSummaryProducer {

    private KafkaProducer producer;

    KafkaSummaryProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "com.example.demo.producer.kafka.KafkaJsonSerializer");
        this.producer = new KafkaProducer(props);
    }

    public void SendMessage(ProducerRecord producerRecord) {
        this.producer.send(producerRecord);
        this.producer.commitTransaction();
    }
}
