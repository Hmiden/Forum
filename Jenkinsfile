pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://gitlab.com/nidhalf00-group/Forums.git', credentialsId: 'forums', branch: 'forums'
            }
        }

        stage('Diagnostic') {
            steps {
                sh 'ls -R'
            }
        }

        stage('Backend - Build & Test') {
            agent {
                docker {
                    image 'maven:3.8.5-openjdk-17'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                script {
                    dir('backend/microservice1') {
                        sh 'mvn clean verify -DskipTests=false'
                    }
                    dir('backend/microservice2') {
                        sh 'mvn clean verify -DskipTests=false'
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
            agent {
                docker {
                    image 'maven:3.8.5-openjdk-17'
                    args '--network micro-network'
                }
            }
            steps {
                script {
                    dir('backend/microservice1') {
                        sh 'mvn sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=admin -Dsonar.password=admin'
                    }
                    dir('backend/microservice2') {
                        sh 'mvn sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=admin -Dsonar.password=admin'
                    }
                }
            }
        }

        stage('Frontend - Build & Test') {
            agent {
                docker {
                    image 'node:18-alpine'
                }
            }
            steps {
                dir('Frontend') {
                    sh 'npm install'
                    // sh 'npm run test:ci' // Décommentez si vous avez configuré ChromeHeadless
                    sh 'npm run build'
                }
            }
        }

        stage('Docker - Deploy') {
            steps {
                // On utilise le docker de l'hôte via le socket partagé
                sh 'docker-compose down'
                sh 'docker-compose up --build -d'
            }
        }
    }

    post {
        always {
            echo 'Nettoyage et fin du pipeline.'
        }
        success {
            echo 'Le projet est déployé avec succès !'
        }
    }
}
