package com.example.sample.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Aspect
@Component
@Slf4j
public class SecurityAspect {

    private String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            log.debug("No authenticated user found");
            return "ANONYMOUS";
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.isEmpty()) {
            log.debug("User has no authorities");
            return "USER";
        }

        String role = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .map(authority -> authority.substring(5)) // Remove "ROLE_" prefix
                .findFirst()
                .orElse("USER");

        log.debug("Current user role: {}", role);
        return role;
    }

    @Around("@annotation(com.example.sample.Validation.Admin)")
    public Object checkAdminRole(ProceedingJoinPoint joinPoint) throws Throwable {
        String role = getCurrentUserRole();

        if (!"ADMIN".equals(role)) {
            log.warn("Access denied: User with role '{}' attempted to access admin-only method", role);
            throw new RuntimeException("Access denied: Admin Only");
        }
        
        log.debug("Admin access granted for user with role: {}", role);
        return joinPoint.proceed();
    }
}
