package com.example.jfrog.consumer.EsClient;

import com.example.jfrog.configuration.Configuration;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class EsClient {

    @Autowired
    Configuration configuration;

    private RestHighLevelClient restHighLevelClient;

    public EsClient() {
        restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost(configuration.getEsNodeHost(), configuration.getEsNodePort(), "http")));
    }

    public void indexDocumentMap(Map<String, Object> jsonMap, String index) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index, "doc")
                .source(jsonMap);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }
}
