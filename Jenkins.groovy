pipeline {
    agent any

    environment {
        SONAR_HOME = tool 'SonarQube-Scanner'
    }

    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', credentialsId: 'GitHub-Jenkins', url: 'https://github.com/ClaZieR/DevOps-Pipeline-Demo.git'
            }
        }
        stage('Sonar Analysis') {
            steps {
                withSonarQubeEnv('Sonar-Jenkins Server') {
                    sh "${SONAR_HOME}/bin/sonar-scanner -Dsonar.projectKey=DevOps-Pipeline-Demo -Dsonar.sources=."
                }
            }
        }
    }
}
