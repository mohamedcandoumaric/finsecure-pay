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