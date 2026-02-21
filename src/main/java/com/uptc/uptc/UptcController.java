package com.uptc.uptc;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UptcController {
    
    private final UptcUserService userService;

    public UptcController(UptcUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUsers, version=1")
    public List<UptcUser> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("createUser")
    public UptcUser createUser(@RequestBody UptcUser uptcUser) {
        return userService.createUser(uptcUser);
    }
    
    
}
