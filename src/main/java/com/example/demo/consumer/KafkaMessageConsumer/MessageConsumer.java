package com.example.demo.consumer.KafkaMessageConsumer;

import com.example.demo.consumer.Entity.EsDocument;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.RebalanceInProgressException;
import org.springframework.util.backoff.ExponentialBackOff;

import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class MessageConsumer implements Runnable {

    private KafkaConsumer kafkaConsumer;

    private String topic;
    private BlockingQueue<EsDocument> blockingQueue;
    private final AtomicBoolean lock = new AtomicBoolean(false);

    public MessageConsumer(Properties properties, String topic, BlockingQueue<EsDocument> blockingQueue) {
        kafkaConsumer = new KafkaConsumer(properties);
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
                kafkaConsumer.subscribe(Arrays.asList(topic));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void addToqueue(ConsumerRecords consumerRecords, BlockingQueue blockingQueue) {
        Iterator<ConsumerRecord> iterator = consumerRecords.iterator();
        while (iterator.hasNext()) {
            blockingQueue.add(iterator.next().value());
        }
    }

    public void commit() {
        synchronized (lock) {
            kafkaConsumer.commitSync();
            lock.set(false);
            lock.notifyAll();
        }
    }

    public void pause(){
        lock.set(true);
    }

}
