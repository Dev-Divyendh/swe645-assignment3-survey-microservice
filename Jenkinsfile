pipeline {
    agent any

    environment {
        AWS_REGION = 'us-east-2'
        ECR_REPO = '585008073814.dkr.ecr.us-east-2.amazonaws.com/swe645-survey-app'
        IMAGE_TAG = 'latest'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/Dev-Divyendh/swe645-assignment3-survey-microservice.git'
            }
        }


        stage('Build JAR') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $ECR_REPO:$IMAGE_TAG .'
            }
        }

        stage('Push to ECR') {
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
            steps {
                sh 'kubectl apply -f deployment.yaml'
            }
        }
    }
}
