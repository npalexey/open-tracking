package com.qlearsite.eng.opentracking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlearsite.eng.opentracking.config.ModuleConfigProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Log4j2
@Service
public class RabbitMqPublisher {

    @Autowired
    private RabbitMqConnector factory;
    @Autowired
    private ModuleConfigProperties configProperties;
    private ObjectMapper mapper = new ObjectMapper();
    private Connection connection;
    private Channel channel;
    private ModuleConfigProperties.Exchange exchange;

    @PostConstruct
    public void init() throws IOException, TimeoutException {
        this.exchange = configProperties.getNotification().getRabbitMq().getExchange();
        connection = factory.createConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(exchange.getName(), exchange.getType(), exchange.isDurable(), exchange.isAutoDelete(), null);
    }

    public void publish(Object object) {
        try {
            channel.basicPublish(exchange.getName(), "", MessageProperties.PERSISTENT_TEXT_PLAIN, mapper.writeValueAsBytes(object));
        } catch (IOException ex) {
            log.error("Error publishing to exchange: " + exchange.getName(), ex);
        }
    }

    @PreDestroy
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (IOException e) {
            log.error("Error closing service.", e);
        }
    }

}
