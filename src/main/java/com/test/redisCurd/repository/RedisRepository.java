package com.test.redisCurd.repository;


import com.test.redisCurd.model.Movie;

import java.util.Map;

public interface RedisRepository {
   Map<Object,Object> getAllMovies();
   void addMovie(Movie movie);
   void deleteMovie(String id);
   Movie findMovie(String id);
}
