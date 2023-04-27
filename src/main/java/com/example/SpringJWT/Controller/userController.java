package com.example.SpringJWT.Controller;

import com.example.SpringJWT.Entity.Movies;
import com.example.SpringJWT.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/User")
public class userController {
    @Autowired
    MovieService service;

    @GetMapping("/viewMovies")
    public List<Movies> getallmovies()
    {
        return  service.getallMovies();
    }
}
