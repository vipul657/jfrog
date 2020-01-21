package com.example.demo.producer.Rest;

import com.example.demo.Configuration;
import com.example.demo.producer.entity.StorageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class StorageTemplate {

   @Autowired
    Configuration configuration;

    private RestTemplate restTemplate = new RestTemplate();

    public Optional<StorageEntity> getRepositorySummary(String url) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-JFrog-Art-API", configuration.getApiKey());
        StorageEntity responseEntity = restTemplate.getForObject(url, StorageEntity.class, httpHeaders);
        return Optional.ofNullable(responseEntity);
    }

}
