pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }

        }
        stage("Build project") {
            steps {
                sh "gradle wrapper && chmod +x gradlew && ./gradlew build"
            }
        }
    }
}
