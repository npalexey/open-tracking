package com.qlearsite.eng.opentracking.service;

import com.qlearsite.eng.opentracking.config.ModuleConfigProperties;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.ForgivingExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class RabbitMqConnector {

    @Autowired
    private ModuleConfigProperties configProperties;

    private ConnectionFactory factory;

    @PostConstruct
    public void init() {
        ModuleConfigProperties.RabbitMq rabbitMq = configProperties.getNotification().getRabbitMq();
        factory = new ConnectionFactory();
        factory.setHost(rabbitMq.getHost());
        factory.setPort(rabbitMq.getPort());
        factory.setUsername(rabbitMq.getUsername());
        factory.setPassword(rabbitMq.getPassword());
        factory.setExceptionHandler(new ForgivingExceptionHandler());
    }

    public Connection createConnection() throws IOException, TimeoutException {
        return factory.newConnection();
    }

}
