package com.nmq.kafka.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by niemengquan on 2017/11/22.
 */
@RestController
public class HelloController {

    @GetMapping(value = "/")
    public String helloSpringBootKafka(){
        return "hello Spring boot integration kafka!";
    }
}
