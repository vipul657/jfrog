package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties
@Component
public class Configuration {

    private String artifactoryUrl;
    private String artifactoryHost;
    private Integer artifactoryPort;
    private String esNodeHost;
    private Integer esNodePort;
    private String kafkaTopic;
    private Long kafkaCommitInterval;
    private String esIndexName;
    private String apiKey;

    public Configuration() {
    }

    public String getArtifactoryUrl() {
        return artifactoryUrl;
    }

    public void setArtifactoryUrl(String artifactoryUrl) {
        this.artifactoryUrl = artifactoryUrl;
    }

    public String getArtifactoryHost() {
        return artifactoryHost;
    }

    public void setArtifactoryHost(String artifactoryHost) {
        this.artifactoryHost = artifactoryHost;
    }

    public Integer getArtifactoryPort() {
        return artifactoryPort;
    }

    public void setArtifactoryPort(Integer artifactoryPort) {
        this.artifactoryPort = artifactoryPort;
    }

    public String getEsNodeHost() {
        return esNodeHost;
    }

    public void setEsNodeHost(String esNodeHost) {
        this.esNodeHost = esNodeHost;
    }

    public Integer getEsNodePort() {
        return esNodePort;
    }

    public void setEsNodePort(Integer esNodePort) {
        this.esNodePort = esNodePort;
    }

    public String getKafkaTopic() {
        return kafkaTopic;
    }

    public void setKafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
    }

    public Long getKafkaCommitInterval() {
        return kafkaCommitInterval;
    }

    public void setKafkaCommitInterval(Long kafkaCommitInterval) {
        this.kafkaCommitInterval = kafkaCommitInterval;
    }

    public String getEsIndexName() {
        return esIndexName;
    }

    public void setEsIndexName(String esIndexName) {
        this.esIndexName = esIndexName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
