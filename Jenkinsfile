pipeline{
    agent any

    stages{
        stage('Git-clone'){
            steps{
                git branch: 'main',
                url: 'https://github.com/iter-anant-bhardwaj/Flappy-Bird.git'
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Run Game') {
            steps {
                bat 'java -jar target\\Maven1-1.0-SNAPSHOT.jar'
            }
        }
    }
}
