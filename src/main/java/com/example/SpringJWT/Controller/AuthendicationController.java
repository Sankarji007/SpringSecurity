package com.example.SpringJWT.Controller;



import com.example.SpringJWT.Entity.RequestClass;
import com.example.SpringJWT.Entity.UsersDB;
import com.example.SpringJWT.Service.AuthendicateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthendicationController {
    @Autowired
    private AuthendicateService service;
    @PostMapping("/register")
    public String Register(@RequestBody RequestClass user)
    {
        System.out.println(user);
        return service.register(user);
//        return "success";
    }
    @PostMapping("/authenticate")
    public String Login(@RequestBody UsersDB user)
    {
        return service.login(user);
    }


}
