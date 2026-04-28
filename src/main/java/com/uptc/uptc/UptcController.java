package com.uptc.uptc;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UptcController {

    private String serverId;
    private final UptcUserService userService;

    public UptcController(UptcUserService userService) {
        this.userService = userService;
        try {
            this.serverId = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            this.serverId = "unknown";
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UptcUser>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        return ResponseEntity.ok(userService.getUsers(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ApiResponse<UptcUser> getUserById(@PathVariable Long id) {
        return new ApiResponse<>(serverId, userService.getUserById(id));
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
}
