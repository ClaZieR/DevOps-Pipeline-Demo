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
        stage('Docker Build Front-End') {
            steps {
                script {
                   withDockerRegistry(credentialsId: 'DockerHub-Jenkins') {
                       sh "docker build -t devops-pipeline-frontend-demo:latest -f front/Dockerfile ."
                       sh "docker tag devops-pipeline-frontend-demo:latest ClaZieR8/devops-pipeline-frontend-demo:latest"
                   }
                }
            }
        }
        stage('Docker Build Back-End') {
            steps {
                script {
                   withDockerRegistry(credentialsId: 'DockerHub-Jenkins') {
                       sh "docker build -t devops-pipeline-backend-demo:latest -f back/Dockerfile ."
                       sh "docker tag devops-pipeline-backend-demo:latest ClaZieR8/devops-pipeline-backend-demo:latest"
                   }
                }
            }
        }
    }
}
