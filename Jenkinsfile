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
                sh "chmod +x gradlew && ./gradlew build"
            }
        }
    }
}
