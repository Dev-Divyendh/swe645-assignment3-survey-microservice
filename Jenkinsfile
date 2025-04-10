pipeline {
    agent none  // No agent at the top level

    environment {
        AWS_REGION = 'us-east-2'
        ECR_REPO = '585008073814.dkr.ecr.us-east-2.amazonaws.com/swe645-survey-app'
        IMAGE_TAG = 'latest'
        GITHUB_CREDENTIALS = 'github-credentials'  // GitHub credentials ID
        DOCKER_CREDENTIALS = 'jenkins-credentials'  // Docker Hub credentials ID (updated to match your Docker credentials ID)
    }


    stages {
        stage('Docker Test') {
            steps {
                script {
                     sh 'docker --version'
                     sh 'docker ps'
                     sh 'docker run hello-world'
                 }
            }
        }
        stage('Checkout Code') {
            agent any  // This will run on any available agent
            steps {
                script {
                    // Checkout code using GitHub credentials
                    git credentialsId: GITHUB_CREDENTIALS, branch: 'main', url: 'https://github.com/Dev-Divyendh/swe645-assignment3-survey-microservice.git'
                }
            }
        }

        stage('Build JAR') {
            steps {
                script {
                    docker.image('maven:3.9.6-eclipse-temurin-17').inside('-v /var/run/docker.sock:/var/run/docker.sock') {
                        sh '''
                            chmod +x ./mvnw
                            ./mvnw clean package -DskipTests
                        '''
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.image('maven:3.9.6-eclipse-temurin-17').inside('-v /var/run/docker.sock:/var/run/docker.sock') {
                        sh '''
                            docker --version
                            docker build -t $ECR_REPO:$IMAGE_TAG .
                        '''
                    }
                }
            }
        }

        stage('Push to ECR') {
            agent any  // This can run on any agent
            steps {
                withAWS(region: "$AWS_REGION", credentials: 'aws-jenkins-creds') {
                    script {
                        // Use Docker credentials for login to Docker Hub and ECR
                        docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS) {
                            sh '''
                                aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $ECR_REPO
                                docker push $ECR_REPO:$IMAGE_TAG
                            '''
                        }
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            agent any  // This can run on any agent
            steps {
                sh 'kubectl apply -f deployment.yaml'
            }
        }
    }
}
