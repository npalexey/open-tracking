package com.qlearsite.eng.opentracking.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Log4j2
@Service
public class TrackingService {

    @Autowired
    private RabbitMqPublisher rabbitMqPublisher;

    public byte[] track(String id) {
        try {
            rabbitMqPublisher.publish(id);
            InputStream in = getClass()
                    .getResourceAsStream("/com/qlearsite/eng/opentracking/image.png");
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            log.error("Error occurred while reading image", e);
            return new byte[]{};
        }
    }
}
