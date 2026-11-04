# FinSecure Pay – Cloud-Native DevOps Pipeline

## Project Overview

FinSecure Pay is a Spring Boot-based transaction validation application developed as part of my DevOps capstone project.

The objective is to demonstrate how an application can be built, tested, scanned for security issues, containerized, deployed to Kubernetes, and monitored using an automated CI/CD pipeline.

## Technologies Used

Application -----Java 21, Spring Boot
Build and Testing----Maven, JUnit
Version Control-----Git, GitHub
CI/CD------Jenkins
Containerization-----Docker, Docker Hub
Orchestration-----Kubernetes
Cloud Deployment------Amazon EKS
Security-------SpotBugs, Trivy
Monitoring------Prometheus, Grafana

## Application Features

The application provides two REST API endpoints:

- `GET /health` – Checks application health.
- `POST /validate` – Validates transaction requests and returns a transaction status.

The application runs locally on port `8088`.

## CI/CD Pipeline

A Jenkins pipeline was configured to automate the following stages:

1. Retrieve source code from GitHub.
2. Build the Spring Boot application using Maven.
3. Execute JUnit tests.
4. Perform static code analysis using SpotBugs.
5. Run dependency and container security checks.
6. Package the application and build a Docker image.
7. Push the Docker image to Docker Hub.
8. Deploy the updated application to local Kubernetes.
9. Verify the deployment.

Automatic GitHub SCM polling was configured in Jenkins to detect source-code changes and trigger the pipeline.

## Testing and Security

JUnit tests were executed successfully, with four tests passing.

SpotBugs was used for static code analysis, and Trivy was used to check container security. The verified scan did not report HIGH or CRITICAL vulnerabilities.

## Kubernetes Deployment

The application was deployed to a local Kubernetes cluster using Docker Desktop.

Two application replicas were maintained to demonstrate availability. Deployment status, service access, application health, and rollback were tested.

**Amazon EKS:** A separate AWS EKS deployment was also completed and verified using an application health check. The AWS resources were subsequently deleted to avoid unnecessary cloud costs.

The Jenkins pipeline deployed to local Kubernetes; automated Jenkins deployment to EKS was not implemented.

## Monitoring and Alerting

Prometheus and Grafana were configured for application infrastructure monitoring.

The monitoring demonstration included CPU and memory metrics, a high-memory alert, and a successful webhook test notification.

## Future Enhancement – AI-Based Monitoring

An AI-based anomaly detection solution is proposed as a future enhancement.

The proposed approach would use machine learning to identify unusual CPU, memory, latency, and error-rate patterns. This feature is not part of the current implementation.

## Running the Application

From the project directory containing `pom.xml`, execute:

`mvn clean test`

`mvn spring-boot:run`

After the application starts, open:

`http://localhost:8088/health`

The endpoint should return:

`FinSecure Pay UP`

## Project Resources

**GitHub Repository:**  
https://github.com/mohamedcandoumaric/finsecure-pay

**Docker Hub Image:**  
`mohamedcandou/finsecure-pay:21`

## Conclusion

This project demonstrates a cloud-native DevOps workflow covering application development, automated testing, continuous integration, security scanning, Docker containerization, Kubernetes deployment, monitoring, and rollback.

The project also provides a foundation for future improvements in cloud deployment automation and AI-driven infrastructure monitoring.