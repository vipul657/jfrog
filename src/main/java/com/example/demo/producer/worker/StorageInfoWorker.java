package com.example.demo.producer.worker;

import com.example.demo.producer.Rest.StorageTemplate;
import com.example.demo.producer.entity.KafkaMessage;
import com.example.demo.producer.entity.RepositorySummary;
import com.example.demo.producer.entity.StorageEntity;
import com.example.demo.producer.kafka.KafkaSummaryProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Optional;

public class StorageInfoWorker implements Runnable {

    private StorageTemplate storageTemplate;
    private KafkaSummaryProducer kafkaSummaryProducer;
    private String storageUrl;
    private String topic;

    public StorageInfoWorker(String topic, String storageUrl, StorageTemplate storageTemplate, KafkaSummaryProducer kafkaSummaryProducer) {
        this.storageTemplate = storageTemplate;
        this.kafkaSummaryProducer = kafkaSummaryProducer;
        this.storageUrl = storageUrl;
        this.topic = topic;
    }

    @Override
    public void run() {
        Optional<StorageEntity> storageEntityOptional = storageTemplate.getRepositorySummary(storageUrl);
        if (storageEntityOptional.isPresent()) {
            RepositorySummary [] repositorySummaries = storageEntityOptional.get().getRepositorySummaries();
            KafkaMessage kafkaMessage = new KafkaMessage(System.currentTimeMillis(),repositorySummaries[repositorySummaries.length-1].getUsedSpace());
            ProducerRecord producerRecord = createMessage(kafkaMessage, this.topic);
            this.kafkaSummaryProducer.SendMessage(producerRecord);
        }
    }

    private ProducerRecord createMessage(KafkaMessage kafkaMessage, String topic) {
        ProducerRecord producerRecord = new ProducerRecord(topic, kafkaMessage);
        return producerRecord;
    }
}