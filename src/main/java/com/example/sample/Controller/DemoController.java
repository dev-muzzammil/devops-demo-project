package com.example.sample.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class DemoController {

    @GetMapping("/public/hello")
    @Operation(summary = "Public endpoint - no authentication required")
    public ResponseEntity<?> publicHello() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello from public endpoint!");
        response.put("timestamp", Instant.now().toString());
        response.put("status", "success");
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/hello")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "User endpoint - requires USER or ADMIN role", 
               security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<?> userHello(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello from user endpoint!");
        response.put("user", authentication.getName());
        response.put("authorities", authentication.getAuthorities());
        response.put("timestamp", Instant.now().toString());
        
        log.info("User {} accessed user endpoint", authentication.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/hello")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Admin endpoint - requires ADMIN role", 
               security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<?> adminHello(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello from admin endpoint!");
        response.put("admin", authentication.getName());
        response.put("authorities", authentication.getAuthorities());
        response.put("timestamp", Instant.now().toString());
        
        log.info("Admin {} accessed admin endpoint", authentication.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/protected/data")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Protected endpoint - any authenticated user", 
               security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<?> protectedData(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "This is protected data!");
        response.put("user", authentication.getName());
        response.put("timestamp", Instant.now().toString());
        response.put("data", Map.of(
            "id", 12345,
            "name", "Sample Data",
            "description", "This is sample protected data"
        ));
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/protected/action")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Protected POST endpoint", 
               security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<?> protectedAction(@RequestBody Map<String, Object> requestData, 
                                            Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Action completed successfully!");
        response.put("user", authentication.getName());
        response.put("receivedData", requestData);
        response.put("timestamp", Instant.now().toString());
        
        log.info("User {} performed protected action with data: {}", 
                authentication.getName(), requestData);
        
        return ResponseEntity.ok(response);
    }
}
