package com.test.redisCurd.controller;

import com.test.redisCurd.model.Movie;
import com.test.redisCurd.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RedisController {
    @Autowired
    private RedisRepository redisRepository;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestParam String key,
                                      @RequestParam String value){
        Movie movie = new Movie(key,value);
        redisRepository.addMovie(movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/values")
    public @ResponseBody Map<String, String> findAll() {
        Map<Object, Object> aa = redisRepository.getAllMovies();
        Map<String, String> map = new HashMap<>();
        for(Map.Entry<Object, Object> entry : aa.entrySet()){
            String key = (String) entry.getKey();
            map.put(key, aa.get(key).toString());
        }
        return map;
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam String key) {
        redisRepository.deleteMovie(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
