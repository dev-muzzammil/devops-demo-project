# DevOps Demo Project

A comprehensive Spring Boot application demonstrating, liquibase, Swagger/OpenAPI, Validation, AOP, Spring Security DevOps best practices including Docker, Kubernetes, CI/CD, monitoring with Prometheus and Grafana, and code quality with Google Checkstyle.

## 🚀 Features

- **Spring Boot 3.5.5** with Java 21
- **PostgreSQL** database with Liquibase migrations
- **Docker** containerization with multi-stage builds
- **Kubernetes** deployment manifests with Kustomize
- **Prometheus** metrics collection
- **Grafana** dashboards for monitoring
- **GitHub Actions** CI/CD pipeline
- **Google Checkstyle** code quality enforcement
- **OpenAPI** documentation

## 📁 Project Structure

```
├── src/main/java/                    # Java source code
├── src/main/resources/               # Configuration files
├── k8s/                              # Kubernetes manifests
│   ├── deployment.yaml               # Application deployment
│   ├── service.yaml                 # Service configuration
│   ├── ingress.yaml                 # Ingress configuration
│   ├── postgres-deployment.yaml     # PostgreSQL deployment
│   └── kustomization.yaml           # Kustomize configuration
├── monitoring/                       # Monitoring configurations
│   ├── prometheus/                  # Prometheus configs
│   └── grafana/                     # Grafana configs
├── .github/workflows/               # GitHub Actions workflows
├── Dockerfile                       # Docker configuration
├── docker-compose.yml              # Local development setup
├── google-checkstyle.xml           # Checkstyle rules
└── build.gradle                    # Gradle build configuration
```

## 🛠️ Prerequisites

- Java 21
- Docker & Docker Compose
- kubectl and kubernetes cluster
- Gradle 8.0+

## 🏃‍♂️ Quick Start

### Local Development

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd devops-demo-project
   ```

2. **Run with Docker Compose**
   ```bash
   docker-compose up -d
   ```

3. **Access the applications**
   - Spring Boot App: http://localhost:8080
   - Prometheus: http://localhost:9090
   - Grafana: http://localhost:3000 (admin/admin)
   - API Documentation: http://localhost:8080/swagger-ui.html

### Manual Build

1. **Build the application**
   ```bash
   ./gradlew build
   ```

2. **Run tests**
   ```bash
   ./gradlew test
   ```

3. **Run checkstyle**
   ```bash
   ./gradlew checkstyleMain checkstyleTest
   ```

## 🐳 Docker

### Build Image
```bash
docker build -t demo-app:latest .
```

### Run Container
```bash
docker run -p 8080:8080 demo-app:latest
```

## ☸️ Kubernetes Deployment

### Deploy to Kubernetes
```bash
# Create namespaces
kubectl create namespace demo-app
kubectl create namespace monitoring

# Deploy application
kubectl apply -f k8s/

# Deploy monitoring stack
kubectl apply -f monitoring/prometheus/
kubectl apply -f monitoring/grafana/

# Check deployment status
kubectl get pods -n demo-app
kubectl get services -n demo-app
```

### Access Services
```bash
# Port forward to access services
kubectl port-forward svc/demo-app-service 8080:80 -n demo-app
kubectl port-forward svc/prometheus-service 9090:9090 -n monitoring
kubectl port-forward svc/grafana-service 3000:3000 -n monitoring
```

## 📊 Monitoring

### Prometheus Metrics
- Application metrics: http://localhost:9090/targets
- JVM metrics: `jvm_memory_used_bytes`, `jvm_threads_live_threads`
- HTTP metrics: `http_server_requests_seconds_count`, `http_server_requests_seconds_sum`
- Custom business metrics can be added using `@Timed`, `@Counted` annotations

### Grafana Dashboards
- Pre-configured Spring Boot dashboard
- JVM Memory and CPU monitoring
- HTTP request metrics
- Database connection pool metrics

## 🔧 CI/CD Pipeline

The GitHub Actions workflow includes:

1. **Test Stage**
   - Unit tests execution
   - Checkstyle validation
   - Build verification

2. **Security Scan**
   - Trivy vulnerability scanning
   - Security report generation

3. **Build & Push**
   - Docker image building
   - Container registry push

4. **Deployment**
   - Staging deployment (develop branch)
   - Production deployment (main branch)
   - Health checks and rollback

### Required Secrets
- `KUBE_CONFIG_STAGING`: Kubernetes config for staging
- `KUBE_CONFIG_PROD`: Kubernetes config for production
- `SLACK_WEBHOOK`: Slack notifications (optional)

## 📏 Code Quality

### Google Checkstyle
Run checkstyle validation:
```bash
./gradlew checkstyleMain checkstyleTest
```

Checkstyle rules are defined in `google-checkstyle.xml` following Google Java Style Guide.

## 🔍 Health Checks

### Application Endpoints
- Health: http://localhost:8080/actuator/health
- Metrics: http://localhost:8080/actuator/metrics
- Prometheus: http://localhost:8080/actuator/prometheus
- Info: http://localhost:8080/actuator/info

## 🌐 Environment Profiles

- **local**: Local development with embedded database
- **docker**: Docker Compose environment
- **kubernetes**: Kubernetes deployment
- **production**: Production settings

## 📝 Development Guidelines

1. **Code Style**: Follow Google Java Style Guide
2. **Commits**: Use conventional commit messages
3. **Branches**: feature/branch-name, hotfix/branch-name
4. **Pull Requests**: Require CI/CD checks and code review

## 🚨 Troubleshooting

### Common Issues

1. **Database Connection**
   - Ensure PostgreSQL is running
   - Check connection string in application.yml

2. **Kubernetes Deployment**
   - Verify namespace exists
   - Check resource limits and requests
   - Review pod logs: `kubectl logs -f <pod-name> -n demo-app`

3. **Monitoring**
   - Prometheus targets should show "UP" status
   - Grafana datasource configuration

### Logs

```bash
# Docker Compose logs
docker-compose logs -f demo-app

# Kubernetes logs
kubectl logs -f deployment/demo-app -n demo-app
```

## 📚 Additional Resources

- [Spring Boot Actuator Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Prometheus Documentation](https://prometheus.io/docs/)
- [Grafana Documentation](https://grafana.com/docs/)
- [Kubernetes Documentation](https://kubernetes.io/docs/)
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)


