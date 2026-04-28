package com.uptc.uptc;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

@Service
public class UptcUserService {

    private final UptcUserRepository userRepository;

    public UptcUserService(UptcUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UptcUser createUser(UptcUser user) {
        try {
            return userRepository.save(user);

        } catch (DataIntegrityViolationException ex) {

            if (ex.getMessage().contains("email")) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "El email ya está registrado");
            }

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se pudo guardar el usuario");
        }
    }

    public Page<UptcUser> getUsers(Pageable pageable) {
        Page<UptcUser> page = userRepository.findAll(pageable);

        if (page.getTotalPages() > 0 && pageable.getPageNumber() >= page.getTotalPages()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "La página " + pageable.getPageNumber() + " no existe");
        }

        return page;
    }

    public UptcUser getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario con ID " + id + " no fue encontrado"));
    }

}
