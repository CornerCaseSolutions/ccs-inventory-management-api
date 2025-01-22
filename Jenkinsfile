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
                sh "./gradlew build"
            }
        }

        stage("Run Tests") {
            steps {
                sh "./gradlew test" //runs tests separately even though gradlew build will also run them 
            }
        }

    }
}
