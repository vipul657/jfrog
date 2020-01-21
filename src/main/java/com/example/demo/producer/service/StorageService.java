package com.example.demo.producer.service;

import com.example.demo.Configuration;
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

    @Autowired
    Configuration configuration;

    @Autowired
    private StorageTemplate storageTemplate;

    @Autowired
    private KafkaSummaryProducer kafkaSummaryProducer;

    private ScheduledExecutorService scheduledExecutorService;

    public void startStorage(){
        System.out.println("Storage service");
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        String storageUrl = configuration.getArtifactoryHost()+":"+configuration.getArtifactoryPort()+configuration.getArtifactoryUrl();
        scheduledExecutorService.scheduleAtFixedRate(new StorageInfoWorker(configuration.getKafkaTopic(),storageUrl,this.storageTemplate,this.kafkaSummaryProducer),0,1, TimeUnit.HOURS);
    }

}

