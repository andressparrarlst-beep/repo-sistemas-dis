package com.uptc.uptc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uptc.uptc.dto.ApiResponse;
import com.uptc.uptc.model.UptcProduct;
import com.uptc.uptc.service.UptcProductService;

@RestController
public class UptcProductController {
    
    @Value("${server.id:no especificado}")
    private String serverId;

    private final UptcProductService productService;

    public UptcProductController (UptcProductService productService){
        this.productService = productService;
    }

    @GetMapping("/getAllProducts")
    public ApiResponse<List<UptcProduct>> getUsers() {
        return new ApiResponse<>(serverId, productService.findAll());
    }

    @PostMapping("createProduct")
    public ApiResponse<UptcProduct> createUser(@RequestBody UptcProduct uptcProduct) {
        return new ApiResponse<UptcProduct>(serverId, productService.save(uptcProduct));
    }
}
