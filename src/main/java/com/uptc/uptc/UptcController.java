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
    private String hostname;
    private final UptcUserService userService;

    public UptcController(UptcUserService userService) {
        this.userService = userService;
        try {
            this.serverId = InetAddress.getLocalHost().getHostAddress();
            this.hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            this.serverId = "unknown";
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UptcUser>> users(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        return ResponseEntity.ok(userService.getUsers(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ApiResponse<UptcUser> userById(@PathVariable Long id) {
        return new ApiResponse<>(serverId,hostname, userService.getUserById(id));
    }

    @PostMapping("user")
    public ApiResponse<UptcUser> user(@RequestBody UptcUser uptcUser) {
        return new ApiResponse<>(serverId,hostname, userService.createUser(uptcUser));
    }

    @GetMapping("/state")
    public ApiResponse<Boolean> state() {
        try {
            InetAddress.getLocalHost();
            return new ApiResponse<>(serverId,hostname,true);
        } catch (UnknownHostException e) {
            return new ApiResponse<>(serverId,hostname, false);
        }
    }
}
