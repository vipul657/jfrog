package com.example.demo.producer.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KafkaSummaryProducer {
    private KafkaProducer producer;

    KafkaSummaryProducer(Properties properties) {
        this.producer = new KafkaProducer(properties);
    }

    public void SendMessage(ProducerRecord producerRecord) {
        this.producer.send(producerRecord);
        this.producer.commitTransaction();
    }
}
