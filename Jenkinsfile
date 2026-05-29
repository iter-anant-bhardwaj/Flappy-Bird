pipeline {
    agent any

    environment {
        IMAGE_NAME = "flappybird"
    }

    stages {

        stage('Git Clone') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/iter-anant-bhardwaj/Flappy-Bird.git'
            }
        }

        stage('Build Maven Project') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %IMAGE_NAME%:latest .'
            }
        }

        stage('Load Image Into Minikube') {
            steps {
                bat 'minikube image load %IMAGE_NAME%:latest'
            }
        }

        stage('Deploy To Kubernetes') {
            steps {
                bat 'kubectl apply -f deployment.yaml'
                bat 'kubectl apply -f service.yaml'
            }
        }

        stage('Verify Deployment') {
            steps {
                bat 'kubectl get deployments'
                bat 'kubectl get pods'
                bat 'kubectl get svc'
            }
        }
    }
}