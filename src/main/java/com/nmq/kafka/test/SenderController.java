package com.nmq.kafka.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *发送消息controller
 * Created by niemengquan on 2017/11/22.
 */
@RestController()
@RequestMapping(value = "sender")
public class SenderController {

    @Autowired
    private  Sender sender;

    @GetMapping(value = "{topicName}/{message}")
    public String sendMessage(@PathVariable String topicName,@PathVariable String message){
        sender.send(topicName, message);
        return "success";
    }
}
