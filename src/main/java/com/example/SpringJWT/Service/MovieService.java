package com.example.SpringJWT.Service;

import com.example.SpringJWT.Entity.MovieRepository;
import com.example.SpringJWT.Entity.Movies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    MovieRepository repository;
    public Movies addMovies(Movies mv) {
        return repository.save(mv);
    }
    public String delMovies(Long id) {
        repository.deleteById(id);
        return  "Deleted Successfully";
    }
    public List<Movies> getallMovies()
    {
        return  repository.findAll();
    }
}
