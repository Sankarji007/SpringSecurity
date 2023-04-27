package com.example.SpringJWT.Controller;

import com.example.SpringJWT.Entity.Movies;
import com.example.SpringJWT.Service.JwtService;
import com.example.SpringJWT.Service.MovieService;
import jakarta.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Admin")
public class controller {
    @Autowired
    private MovieService movieService;

    @PostMapping("/addmovies")
    public Movies addmovies(@RequestBody Movies mv)
    {

        return movieService.addMovies(mv);
    }
    @GetMapping("/deletemovie/{id}")
    public String deletemovie(@PathVariable Long id)
    {
        return movieService.delMovies(id);
    }
}
