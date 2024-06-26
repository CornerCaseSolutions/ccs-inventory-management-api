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
                sh "cd ./InventoryManagement && gradlew build"
            }
        }
    }
}
