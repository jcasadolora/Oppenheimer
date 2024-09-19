pipeline {

    agent any  // Use any available agent

    environment {
        // Define environment variables if needed
        JAVA_HOME = tool 'JDK 21'  // Specify your JDK version
    }
    stages {
        stage('Build') {
            steps {
                script {
                    // Clean and build the project
                    sh './gradlew clean build'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Run tests
                    sh './gradlew test'
                }
            }
        }

        stage('Code Coverage') {
            steps {
                script {
                    // Generate coverage report
                    sh './gradlew jacocoTestReport'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Deploy your application (adjust the command as necessary)
                    sh 'deploy.sh'  // Example deploy script
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline succeeded!'
            // Optionally, you can send notifications or perform additional actions
        }
        failure {
            echo 'Pipeline failed!'
            // Optionally, handle failures (e.g., send notifications)
        }
    }
}
