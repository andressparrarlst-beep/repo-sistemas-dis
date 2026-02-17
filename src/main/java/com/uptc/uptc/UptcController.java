package com.uptc.uptc;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class UptcController {
    
    private final UptcUserService userService;

    public UptcController(UptcUserService userService) {
        this.userService = userService;
    }

    @PostMapping("getAllUsers")
    public List<UptcUser> getUsers(@RequestBody String entity) {
        //TODO: process POST request
        
        return userService.getAllUsers();
    }

    @PostMapping("createUser")
    public UptcUser createUser(@RequestBody UptcUser uptcUser) {
        //TODO: process POST request
        
        return userService.createUser(uptcUser);
    }
    
    
}
