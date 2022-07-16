package com.musinsa.assignment.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.max-heap-size}")
    private String maxHeapSize;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() {
        redisServer = RedisServer.builder()
            .port(port)
            .setting("maxmemory " + maxHeapSize)
            .build();

        try {
            redisServer.start();
        } catch (Exception e) {
            if (!e.getMessage().equals("Can't start redis server. Check logs for details.")) {
                throw e;
            }
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null && redisServer.isActive()) {
            redisServer.stop();
        }
    }
}
