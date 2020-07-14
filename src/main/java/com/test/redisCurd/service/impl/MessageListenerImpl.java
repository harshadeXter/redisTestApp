package com.test.redisCurd.service.impl;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageListenerImpl implements MessageListener {
    public static List<String> messageList = new ArrayList<>();
    @Override
    public void onMessage(Message message, byte[] bytes) {
        messageList.add(message.toString());
        System.out.println("Message successfully Received: " + new String(message.getBody()));
    }
}
