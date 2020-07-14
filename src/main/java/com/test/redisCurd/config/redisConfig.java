package com.test.redisCurd.config;

import com.test.redisCurd.service.MessagePublisher;
import com.test.redisCurd.service.impl.MessageListenerImpl;
import com.test.redisCurd.service.impl.MessagePublisherImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class redisConfig {
    @Bean
    JedisConnectionFactory jedisConnectionFactory(){
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(){
        final RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }

    @Bean
    ChannelTopic topic(){
        return new ChannelTopic("publishSubscriber:queue");
    }

    @Bean
    public MessagePublisher pushToRedis(){
        return new MessagePublisherImpl(redisTemplate(),topic());
    }

    @Bean
    MessageListenerAdapter messageListener(){
        return new MessageListenerAdapter(new MessageListenerImpl());
    }
}
