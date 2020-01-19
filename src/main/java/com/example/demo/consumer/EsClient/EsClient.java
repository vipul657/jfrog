package com.example.demo.consumer.EsClient;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class EsClient {

    @Value("${spring.es.node.host}")
    private String esNodeHost;

    @Value(("${spring.es.node.port}"))


    private Integer esNodePort;
    private RestHighLevelClient restHighLevelClient;

    public EsClient(){
        restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost(esNodeHost,esNodePort,"http")));
    }

    public void indexDocumentMap(Map<String,Object> jsonMap,String index ) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index, "doc")
                .source(jsonMap);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }
}
