package com.nmq.kafka.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by niemengquan on 2017/11/22.
 */
@SpringBootApplication
public class SpringKafkaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringKafkaApplication.class, args);
        //从容器中获取bean
        Sender sender = run.getBean(Sender.class);
        sender.send("niemq","this is a spring boot message!");
    }
}
