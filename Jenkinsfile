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

                stage('Docker Build') {
            steps {
                echo 'Building FinSecure Pay Docker image'
                bat 'docker build -t finsecure-pay:1.0 .'
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
                        echo %DOCKER_TOKEN% | docker login -u %DOCKER_USER% --password-stdin
                        docker tag finsecure-pay:1.0 %DOCKER_USER%/finsecure-pay:1.0
                        docker push %DOCKER_USER%/finsecure-pay:1.0
                        docker logout
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