pipeline{
    agent any

    stages{
        stage('Git-clone'){
            step{
                git branch: 'main',
                url: 'https://github.com/iter-anant-bhardwaj/Flappy-Bird.git'
            }
        }
        stage('Execute'){
            step{
                bat ''' mvn clean package
                java -jar target\Maven1-1.0-SNAPSHOT.jar '''
            }
        }
    }
}
