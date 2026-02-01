# 🔐 Security Guidelines

This document outlines security best practices for this demo project.

## ⚠️ Important Security Notes

### For Demo/Learning Purposes Only
This project is designed for **educational purposes** and **local development**. Before using in production:

1. **Change all default passwords**
2. **Use strong, unique credentials**
3. **Enable proper authentication/authorization**
4. **Configure network policies**
5. **Set up proper RBAC**

## 🚫 What NOT to Commit

Never commit the following to version control:
- `k8s/secrets.yaml` - Contains sensitive database credentials
- `.env` files - Environment variables with secrets
- API keys, tokens, or certificates
- Real passwords or credentials

## ✅ Secure Deployment Options

### Option 1: CI/CD with GitHub Secrets (Recommended)
The GitHub Actions pipeline automatically creates secrets from GitHub Secrets:

```bash
# Set these in GitHub repository settings:
# DB_USERNAME, DB_PASSWORD, DB_NAME
```

### Option 2: Manual Secret Creation
```bash
kubectl create secret generic demo-app-secrets \
  --from-literal=db-username=your_secure_username \
  --from-literal=db-password=your_secure_password \
  --from-literal=db-name=your_database_name \
  --namespace=demo-app
```

### Option 3: Sealed Secrets
For production, consider using [Sealed Secrets](https://github.com/bitnami-labs/sealed-secrets).

## 🔒 Security Hardening Checklist

### Kubernetes Security
- [ ] Enable Network Policies
- [ ] Configure Pod Security Policies
- [ ] Use RBAC with least privilege
- [ ] Enable security contexts for pods
- [ ] Use read-only filesystems where possible
- [ ] Scan images for vulnerabilities

### Application Security
- [ ] Enable Spring Security
- [ ] Use HTTPS/TLS
- [ ] Implement proper authentication
- [ ] Add rate limiting
- [ ] Enable CORS properly
- [ ] Validate all inputs

### Database Security
- [ ] Use strong passwords
- [ ] Enable SSL/TLS connections
- [ ] Limit database user privileges
- [ ] Enable audit logging
- [ ] Regular backups

## 🛡️ Local Development Security

When running locally:
```bash
# Use environment variables instead of hardcoded values
export DB_PASSWORD="your_secure_password"
export DB_USERNAME="your_username"

# Or use .env files (add to .gitignore!)
echo "DB_PASSWORD=your_secure_password" > .env.local
```

## 📊 Monitoring Security Events

Set up alerts for:
- Failed authentication attempts
- Unusual API access patterns
- Pod restarts due to security issues
- Changes to secrets/configmaps

## 🔄 Regular Security Tasks

- [ ] Rotate secrets regularly
- [ ] Update dependencies
- [ ] Scan for vulnerabilities
- [ ] Review access logs
- [ ] Audit RBAC permissions

## 🚨 Immediate Actions Required

1. **Add `k8s/secrets.yaml` to `.gitignore`** ✅ (Already done)
2. **Set up GitHub Secrets** for CI/CD deployment
3. **Change default credentials** before any deployment
4. **Enable security scanning** in CI/CD pipeline


---

**Remember**: This is a learning project!.
