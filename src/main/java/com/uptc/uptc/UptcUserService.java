package com.uptc.uptc;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UptcUserService {
    
    private final UptcUserRepository userRepository;
    
    public UptcUserService(UptcUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UptcUser createUser(UptcUser user) {
        return userRepository.save(user);
    }

    public List<UptcUser> getAllUsers() {
        return userRepository.findAll();
    }

}
