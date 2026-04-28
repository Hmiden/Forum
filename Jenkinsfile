pipeline {
    agent any

    environment {
        DOCKER_COMPOSE_VERSION = '1.29.2'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Backend - Build & Test') {
            parallel {
                stage('Microservice 1') {
                    steps {
                        dir('backend/microservice1') {
                            sh './mvnw clean verify'
                        }
                    }
                }
                stage('Microservice 2') {
                    steps {
                        dir('backend/microservice2') {
                            sh './mvnw clean verify'
                        }
                    }
                }
            }
        }

        stage('Backend - SonarQube Analysis') {
            steps {
                parallel(
                    "MS1 Analysis": {
                        dir('backend/microservice1') {
                            // Remarque : Remplacez par votre sonar.login token si nécessaire
                            sh './mvnw sonar:sonar -Dsonar.host.url=http://sonarqube:9000'
                        }
                    },
                    "MS2 Analysis": {
                        dir('backend/microservice2') {
                            sh './mvnw sonar:sonar -Dsonar.host.url=http://sonarqube:9000'
                        }
                    }
                )
            }
        }

        stage('Frontend - Build & Test') {
            steps {
                dir('Frontend') {
                    sh 'npm install'
                    sh 'npm run test:ci'
                    sh 'npm run build'
                }
            }
        }

        stage('Docker - Build & Deploy') {
            steps {
                sh 'docker-compose down'
                sh 'docker-compose up --build -d'
            }
        }
    }

    post {
        always {
            junit 'backend/**/target/surefire-reports/*.xml'
            // Optionnel : archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Please check the logs.'
        }
    }
}
