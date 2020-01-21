package com.example.jfrog.producer.service;

import com.example.jfrog.configuration.Configuration;
import com.example.jfrog.producer.Rest.StorageTemplate;
import com.example.jfrog.producer.kafka.KafkaSummaryProducer;
import com.example.jfrog.producer.worker.StorageInfoWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class StorageService {

    @Autowired
    Configuration configuration;

    @Autowired
    private StorageTemplate storageTemplate;

    @Autowired
    private KafkaSummaryProducer kafkaSummaryProducer;

    private ScheduledExecutorService scheduledExecutorService;

    public StorageService(){
        System.out.println("Storage service");
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        String storageUrl = configuration.getArtifactoryHost()+":"+configuration.getArtifactoryPort()+configuration.getArtifactoryUrl();
        scheduledExecutorService.scheduleAtFixedRate(new StorageInfoWorker(configuration.getKafkaTopic(),storageUrl,this.storageTemplate,this.kafkaSummaryProducer),0,1, TimeUnit.HOURS);
    }

}

