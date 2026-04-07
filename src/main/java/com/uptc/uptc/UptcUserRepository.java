package com.uptc.uptc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UptcUserRepository extends JpaRepository<UptcUser, Long> {
    
    Page<UptcUser> findAll(Pageable pageable);

}
