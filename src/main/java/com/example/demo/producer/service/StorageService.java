package com.example.demo.producer.service;

import com.example.demo.producer.Rest.StorageTemplate;
import com.example.demo.producer.kafka.KafkaSummaryProducer;
import com.example.demo.producer.worker.StorageInfoWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class StorageService {

    @Value("${artifactory.url}")
    private String url;

    @Value("${artifactory.host}")
    private String host;

    @Value("${artifactory.port}")
    private String port;

    @Value("${kafka.topic}")
    private String topic;

    @Autowired
    private StorageTemplate storageTemplate;

    @Autowired
    private KafkaSummaryProducer kafkaSummaryProducer;

    private ScheduledExecutorService scheduledExecutorService;

    /**
     * Service that polls the storage appi at fixed intervals for storage metrics
     * */
    StorageService(){
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        String storageUrl = host+":"+port+url;
        scheduledExecutorService.scheduleAtFixedRate(new StorageInfoWorker(this.topic,storageUrl,this.storageTemplate,this.kafkaSummaryProducer),0,1, TimeUnit.HOURS);
    }

}

