package com.example.jfrog.consumer.KafkaMessageConsumer;

import com.example.jfrog.consumer.Entity.EsDocument;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.RebalanceInProgressException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Wrapper class for kafka consumer providing abilities to read exactly once.
 */
public class MessageConsumer implements Runnable {

    private KafkaConsumer kafkaConsumer;
    private static Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    private String topic;
    private BlockingQueue<EsDocument> blockingQueue;
    private final AtomicBoolean lock = new AtomicBoolean(false);

    public MessageConsumer(String topic, BlockingQueue<EsDocument> blockingQueue) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "com.example.demo.consumer.KafkaMessageConsumer.KafkaJsonDeserializer");
        kafkaConsumer = new KafkaConsumer(props);
        kafkaConsumer.subscribe(Arrays.asList(topic));
        this.blockingQueue = blockingQueue;
    }


    @Override
    public void run() {
        while (true) {
            try {
                synchronized (lock) {
                    while (lock.get()) {
                        lock.wait();
                    }
                    ConsumerRecords records = kafkaConsumer.poll(Duration.ofMillis(10000));
                    addToqueue(records, blockingQueue);
                }

            } catch (CommitFailedException | RebalanceInProgressException e) {
                logger.error("Error occureed while polling, retrying to connecct to kafka brokers");
                kafkaConsumer.subscribe(Arrays.asList(topic));
            } catch (InterruptedException e) {
                logger.error("Consumer thread Interrupted");
                e.printStackTrace();
            }
        }
    }

    public void commit() {
        synchronized (lock) {
            kafkaConsumer.commitSync();
            lock.set(false);
            lock.notifyAll();
        }
    }

    public void pause() {
        lock.set(true);
    }

    private void addToqueue(ConsumerRecords consumerRecords, BlockingQueue blockingQueue) {
        Iterator<ConsumerRecord> iterator = consumerRecords.iterator();
        while (iterator.hasNext()) {
            blockingQueue.add(iterator.next().value());
        }
    }


}
