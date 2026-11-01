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