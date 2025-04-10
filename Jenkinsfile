pipeline {
    agent none  // No agent at the top level

    environment {
        AWS_REGION = 'us-east-2'
        ECR_REPO = '585008073814.dkr.ecr.us-east-2.amazonaws.com/swe645-survey-app'
        IMAGE_TAG = 'latest'
    }

    stages {
        stage('Checkout Code') {
            agent any  // This will run on any available agent
            steps {
                git branch: 'main', url: 'https://github.com/Dev-Divyendh/swe645-assignment3-survey-microservice.git'
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
                    sh '''
                        aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $ECR_REPO
                        docker push $ECR_REPO:$IMAGE_TAG
                    '''
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
