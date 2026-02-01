# 🔐 JWT Authentication Implementation

This document describes the JWT (JSON Web Token) authentication implementation in the Spring Boot application.

## 🏗️ Architecture Overview

The JWT authentication system consists of the following components:

### Security Components
- **`JwtTokenProvider`** - Generates and validates JWT tokens
- **`JwtAuthenticationFilter`** - Filters requests and validates JWT tokens
- **`JwtAuthenticationEntryPoint`** - Handles authentication errors
- **`SecurityConfig`** - Main security configuration

### Authentication Components
- **`AuthController`** - Handles authentication endpoints
- **`LoginRequest`** - DTO for login requests
- **`RegisterRequest`** - DTO for user registration

## 🔑 JWT Configuration

### Application Properties
```yaml
app:
  jwt:
    secret: ${JWT_SECRET:mySecretKey123456789012345678901234567890}
    expiration: ${JWT_EXPIRATION:86400000} # 24 hours
```

### Environment Variables
- `JWT_SECRET` - Secret key for signing JWT tokens
- `JWT_EXPIRATION` - Token expiration time in milliseconds

## 🚀 API Endpoints

### Authentication Endpoints (Public)
- `POST /api/auth/login` - User login and token generation
- `POST /api/auth/register` - User registration
- `POST /api/auth/refresh` - Token refresh
- `GET /api/auth/me` - Get current user info
- `POST /api/auth/logout` - User logout

### Protected Endpoints
- `GET /api/user/hello` - Requires USER or ADMIN role
- `GET /api/admin/hello` - Requires ADMIN role
- `GET /api/protected/data` - Any authenticated user
- `POST /api/protected/action` - USER or ADMIN role

### Public Endpoints
- `GET /api/public/hello` - No authentication required

## 📝 Usage Examples

### 1. User Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password"
  }'
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "username": "admin",
  "roles": ["ROLE_ADMIN"],
  "expiresAt": "2024-01-02T12:00:00Z"
}
```

### 2. Access Protected Endpoint
```bash
curl -X GET http://localhost:8080/api/user/hello \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

### 3. Register New User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "email": "user@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

## 🔒 Security Features

### JWT Token Structure
```json
{
  "sub": "username",
  "roles": ["ROLE_USER", "ROLE_ADMIN"],
  "iat": 1640995200,
  "exp": 1641081600
}
```

### Security Headers
- `Authorization: Bearer <token>` - Required for protected endpoints
- CORS configuration for cross-origin requests
- CSRF protection disabled for stateless JWT

### Password Security
- BCrypt password hashing
- Minimum password length: 6 characters
- Password validation during registration

## 🛡️ Security Best Practices

### Production Considerations
1. **Strong JWT Secret**: Use a long, random secret key
2. **Token Expiration**: Set appropriate expiration times
3. **HTTPS**: Always use HTTPS in production
4. **Token Refresh**: Implement token refresh mechanism
5. **Rate Limiting**: Add rate limiting to auth endpoints

### Environment Variables
```bash
# Production
JWT_SECRET=your-super-secure-random-secret-key-here
JWT_EXPIRATION=3600000  # 1 hour

# Development (default values)
JWT_SECRET=mySecretKey123456789012345678901234567890
JWT_EXPIRATION=86400000  # 24 hours
```

## 🔧 Configuration Details

### Security Configuration
```java
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    // JWT authentication filter configuration
    // CORS configuration
    // Endpoint security rules
}
```

### JWT Filter Chain
1. Request comes in
2. `JwtAuthenticationFilter` extracts JWT from Authorization header
3. Token is validated using secret key
4. User authentication is set in SecurityContext
5. Request proceeds to controller

## 📊 Monitoring & Logging

### Authentication Events
- Login attempts (success/failure)
- Token validation failures
- Protected endpoint access

### Log Levels
```yaml
logging:
  level:
    com.example.demoapp.security: debug
```

## 🧪 Testing

### Test Authentication
```bash
# Test login with invalid credentials
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "invalid", "password": "wrong"}'

# Test protected endpoint without token
curl -X GET http://localhost:8080/api/user/hello

# Test protected endpoint with invalid token
curl -X GET http://localhost:8080/api/user/hello \
  -H "Authorization: Bearer invalid-token"
```

## 🚨 Common Issues

### Token Validation Failures
- Check JWT secret configuration
- Verify token format and expiration
- Ensure proper Authorization header format

### CORS Issues
- Verify CORS configuration in SecurityConfig
- Check preflight OPTIONS requests

### Authentication Issues
- Ensure proper role-based access
- Check SecurityContext authentication
- Verify JWT token claims

## 📚 Additional Resources

- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [JWT Specification](https://tools.ietf.org/html/rfc7519)
- [OWASP JWT Security Guidelines](https://owasp.org/www-project-jwt-cheat-sheet/)
