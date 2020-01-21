package com.example.jfrog.producer.worker;

import com.example.jfrog.producer.Rest.StorageTemplate;
import com.example.jfrog.producer.entity.KafkaMessage;
import com.example.jfrog.producer.entity.RepositorySummary;
import com.example.jfrog.producer.entity.StorageEntity;
import com.example.jfrog.producer.kafka.KafkaSummaryProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class StorageInfoWorker implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(StorageInfoWorker.class);
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
            KafkaMessage kafkaMessage = new KafkaMessage(System.currentTimeMillis(),Long.parseLong(repositorySummaries[repositorySummaries.length-1].getUsedSpace()));
            ProducerRecord producerRecord = createMessage(kafkaMessage, this.topic);
            logger.info("Sending data to kafka topic :"+topic);
            this.kafkaSummaryProducer.SendMessage(producerRecord);
        }
    }

    private ProducerRecord createMessage(KafkaMessage kafkaMessage, String topic) {
        ProducerRecord producerRecord = new ProducerRecord(topic, kafkaMessage);
        return producerRecord;
    }
}
