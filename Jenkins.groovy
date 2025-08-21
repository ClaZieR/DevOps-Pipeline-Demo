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
                       sh "docker build -t devops-pipeline-frontend-demo:latest -f frontend/Dockerfile ./frontend"
                       sh "docker tag devops-pipeline-frontend-demo:latest clazier8/devops-pipeline-frontend-demo:latest"
                       sh "docker push clazier8/devops-pipeline-frontend-demo:latest"
                   }
                }
            }
        }
        stage('Docker Build Back-End') {
            steps {
                script {
                   withDockerRegistry(credentialsId: 'DockerHub-Jenkins') {
                       sh "docker build -t devops-pipeline-backend-demo:latest -f backend/Dockerfile ./backend"
                       sh "docker tag devops-pipeline-backend-demo:latest clazier8/devops-pipeline-backend-demo:latest"
                       sh "docker push clazier8/devops-pipeline-backend-demo:latest"
                   }
                }
            }
        }
        stage('Kubernetes Deployment') {
            steps {
                withKubeConfig(caCertificate: '', clusterName: 'kubernetes', contextName: '', credentialsId: 'Kube-Jenkins', namespace: '', restrictKubeConfigAccess: false, serverUrl: '192.168.1.42:6443') {
                    sh "kubectl apply -f kube-dep.yaml"
                }
            }
        }
    }
}
