package com.uptc.uptc;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UptcController {

    @Value("${server.id:no especificado}")
    private String serverId;

    private final UptcUserService userService;
    private List<byte[]> memory = new ArrayList<>();

    public UptcController(UptcUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUsers")
    public ApiResponse<List<UptcUser>> getUsers() {
        return new ApiResponse<>(serverId, userService.getAllUsers());
    }

    @PostMapping("createUser")
    public ApiResponse<UptcUser> createUser(@RequestBody UptcUser uptcUser) {
        return new ApiResponse<>(serverId, userService.createUser(uptcUser));
    }

    @GetMapping("/ip")
    public ApiResponse<String> getServerIp() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return new ApiResponse<>(serverId, "IP del servidor: " + ip.getHostAddress());
        } catch (UnknownHostException e) {
            return new ApiResponse<>(serverId, "No se pudo obtener la IP");
        }
    }

    @GetMapping("/cpu")
    public String cpu() {

        while (true) {
            Math.sqrt(Math.random());
        }

    }

    @GetMapping("/memory")
    public String memoryLeak() {
        memory.add(new byte[1024 * 1024]); // 1 MB por request
        return "Memoria consumida: " + memory.size() + " MB";
    }
}
