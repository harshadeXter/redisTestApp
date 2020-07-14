package com.test.redisCurd.repository.impl;

import com.test.redisCurd.model.Movie;
import com.test.redisCurd.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
    private static final String KEY = "Movie";

    private RedisTemplate<String,Object> redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Map<Object, Object> getAllMovies() {
        return hashOperations.entries(KEY);
    }

    @Override
    public void addMovie(Movie movie) {
        hashOperations.put(KEY,movie.getId(),movie.getName());
    }

    @Override
    public void deleteMovie(String id) {
        hashOperations.delete(KEY,id);
    }

    @Override
    public Movie findMovie(String id) {
        return (Movie)hashOperations.get(KEY,id);
    }
}
