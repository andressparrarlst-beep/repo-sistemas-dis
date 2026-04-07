package com.uptc.uptc;


import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class UptcUserService {

    private final UptcUserRepository userRepository;

    public UptcUserService(UptcUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UptcUser createUser(UptcUser user) {
        return userRepository.save(user);
    }

    public Page<UptcUser> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public UptcUser getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

}
