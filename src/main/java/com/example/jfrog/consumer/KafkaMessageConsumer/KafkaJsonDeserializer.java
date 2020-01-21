package com.example.jfrog.consumer.KafkaMessageConsumer;

import com.example.jfrog.consumer.Entity.EsDocument;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Map;

public class KafkaJsonDeserializer implements Deserializer {

    private Logger logger = LogManager.getLogger(this.getClass());


    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public Object deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        EsDocument obj = null;
        try {
            obj = mapper.readValue(bytes, EsDocument.class);
        } catch (Exception e) {

            logger.error(e.getMessage());
        }
        return obj;
    }

    @Override
    public void close() {

    }
}
