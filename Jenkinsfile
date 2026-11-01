
pipeline {
    agent any

    environment {
        IMAGE_NAME = 'mohamedcandou/finsecure-pay'
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('Build and Test') {
            steps {
                echo 'Building and testing FinSecure Pay'
                bat 'mvn clean test'
            }
        }

        stage('Static Code Analysis') {
    steps {
        echo 'Running SpotBugs static code analysis'
        bat 'mvn com.github.spotbugs:spotbugs-maven-plugin:4.9.8.2:check'
    }
}

        stage('Package') {
            steps {
                echo 'Packaging Spring Boot application'
                bat 'mvn package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building versioned Docker image'
                bat '''
                    @echo off
                    docker build -t %IMAGE_NAME%:%IMAGE_TAG% .
                    if errorlevel 1 exit /b 1
                '''
            }
        }

        
stage('Trivy Security Scan') {
    steps {
        echo 'Scanning Docker image for HIGH and CRITICAL vulnerabilities'
        bat '''
            @echo off
            docker run --rm ^
              -v /var/run/docker.sock:/var/run/docker.sock ^
              aquasec/trivy:0.76.1 image ^
              --image-src docker ^
              --scanners vuln ^
              --severity HIGH,CRITICAL ^
              --exit-code 1 ^
              %IMAGE_NAME%:%IMAGE_TAG%

            if errorlevel 1 exit /b 1
        '''
    }
}


        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_TOKEN'
                )]) {
                    bat '''
                        @echo off
                        echo %DOCKER_TOKEN%| docker login -u %DOCKER_USER% --password-stdin
                        if errorlevel 1 exit /b 1

                        docker push %IMAGE_NAME%:%IMAGE_TAG%
                        set PUSH_RESULT=%ERRORLEVEL%

                        docker logout
                        exit /b %PUSH_RESULT%
                    '''
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withCredentials([file(
                    credentialsId: 'kubeconfig-docker-desktop',
                    variable: 'KUBECONFIG'
                )]) {
                    bat '''
                        @echo off
                        kubectl apply -f k8s/configmap.yaml
                        if errorlevel 1 exit /b 1

                        kubectl apply -f k8s/deployment.yaml
                        if errorlevel 1 exit /b 1

                        kubectl apply -f k8s/service.yaml
                        if errorlevel 1 exit /b 1

                        kubectl set image deployment/finsecure-pay finsecure-pay=%IMAGE_NAME%:%IMAGE_TAG%
                        if errorlevel 1 exit /b 1

                        kubectl rollout status deployment/finsecure-pay --timeout=180s
                        if errorlevel 1 exit /b 1
                    '''
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                withCredentials([file(
                    credentialsId: 'kubeconfig-docker-desktop',
                    variable: 'KUBECONFIG'
                )]) {
                    bat '''
                        @echo off
                        kubectl get deployment finsecure-pay
                        if errorlevel 1 exit /b 1

                        kubectl get pods -l app=finsecure-pay
                        if errorlevel 1 exit /b 1
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'FinSecure Pay CI/CD pipeline completed successfully!'
        }
        failure {
            echo 'FinSecure Pay pipeline failed. Check the build logs.'
        }
    }
}