package com.qlearsite.eng.opentracking.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "module")
public class ModuleConfigProperties {

    @NotEmpty
    private String id;
    @NotNull
    private Security security;
    @NotNull
    private Notification notification;

    @Data
    public static class Security {

        @NotEmpty
        private String publicKeyPath;
        @NotNull
        private Debug debug;

        @Data
        public static class Debug {

            private boolean enabled;
        }
    }

    @Data
    public static class Notification {

        @NotNull
        private RabbitMq rabbitMq;
    }

    @Data
    public static class RabbitMq {

        @NotEmpty
        private String host;
        private int port;
        private String username;
        private String password;
        private Exchange exchange;
    }

    @Data
    public static class Exchange {
        @NotEmpty
        private String name;
        @NotEmpty
        private String type;
        private boolean durable;
        private boolean autoDelete;
    }

}
