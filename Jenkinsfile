pipeline {
    agent any

    stages {
        stage('Build and Test') {
            steps {
                echo 'Building FinSecure Pay application'
                bat 'mvn clean test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging FinSecure Pay application'
                bat 'mvn clean package'
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

                docker tag finsecure-pay:1.0 %DOCKER_USER%/finsecure-pay:1.0
                if errorlevel 1 exit /b 1

                docker push %DOCKER_USER%/finsecure-pay:1.0
                if errorlevel 1 exit /b 1

                docker logout
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

                kubectl rollout status deployment/finsecure-pay --timeout=120s
                if errorlevel 1 exit /b 1
            '''
        }
    }
}
               
    }

    post {
        success {
            echo 'FinSecure Pay pipeline completed successfully!'
        }
        failure {
            echo 'FinSecure Pay pipeline failed. Check the build logs.'
        }
    }
}