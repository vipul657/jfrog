package com.example.demo.consumer.service;

import com.example.demo.consumer.Entity.EsDocument;
import com.example.demo.consumer.EsClient.EsClient;
import com.example.demo.consumer.KafkaMessageConsumer.MessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 *  Service to read messages from kafka and push them to elastic search
 * */
@Service
public class MessageProcessor {

    /**
     * Blocking queue to read a certain number of messages from kafka
     * */
    private BlockingQueue<EsDocument> blockingQueue;

    @Value("${kafka.topic}")
    private String topic;

    @Value("${kafka.commit.interval}")
    private Long commitInterval;

    @Value("${es.index.name}")
    private String esIndex;

    @Value("${kafka.blocking.queue.size}")
    private Integer size;

    @Autowired
    EsClient esClient;

    private List<EsDocument> esDocumentList;
    private MessageConsumer messageConsumer;
    private ExecutorService executorService;
    private static Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    public MessageProcessor(Properties properties, String topic) {
        this.messageConsumer = new MessageConsumer(properties, topic, blockingQueue);
        this.executorService = Executors.newFixedThreadPool(1);
        this.esDocumentList = new ArrayList<>();
        startConsumingFromKafkaAndPushToEs();
        this.blockingQueue = new ArrayBlockingQueue<>(size);
    }

    private void startConsumingFromKafkaAndPushToEs() {
        this.executorService.submit(this.messageConsumer);
        Long t1 = Long.valueOf(0);
        while (true) {
            if (this.blockingQueue.size() == size || isTimeElapsed(t1)) {
                logger.info("Commit invoked");
                messageConsumer.pause();
                blockingQueue.drainTo(esDocumentList);
                startPushingToElasticSearch();
                esDocumentList= new ArrayList<>();
                messageConsumer.commit();
                logger.info("Commit done");
                t1 = System.currentTimeMillis();
            } else {
                try {
                    esDocumentList.add(blockingQueue.take());
                } catch (InterruptedException e) {
                    logger.error("Reader thread interrupted");
                    e.printStackTrace();
                }
            }
        }
    }

    private void startPushingToElasticSearch() {
        while (!esDocumentList.isEmpty()) {
            esDocumentList.forEach(esDocument -> {
                Map<String, Object> esMap = new HashMap<>();
                esMap.put("storageUsed", esDocument.getUsedStorage());
                esMap.put("timeStamp", esDocument.getTimeStamp());
                try {
                    esClient.indexDocumentMap(esMap, this.esIndex);
                    logger.info("pushing to elastic search");
                } catch (IOException e) {
                    logger.error("Error while processing data to elastic search");
                    e.printStackTrace();
                }
            });
        }
    }

    private boolean isTimeElapsed(long t1) {
        if (System.currentTimeMillis() - t1 >= commitInterval) {
            return true;
        }
        return false;
    }

}
