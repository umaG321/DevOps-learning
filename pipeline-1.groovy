pipeline {
    agent any
    stages {
        stage ('Checkout'){
            steps {
                git checkout '<git-repo-url>'
            }
        }
        stage ('Build'){
            steps {
                sh 'mvn clean package'
            }
        }
        stage ('Unit Test'){
            steps {
                sh 'mvn test'
            }
        }
        stage ('Integration Test'){
            steps {
                sh 'mvn integration-test'
            }
        } 
        stage ('Deploy to QA') {
            steps {
                sh 'kubectl apply -f qa-deployment.yaml'
            }
        }
        stage ('Smoke Test') {
            steps {
                sh 'bash smoke_test.sh'
            }
        }
        stage ('Deploy to Production') {
            steps {
                input 'proceed with deployment to Production'
                sh 'kubectl apply -f production-deployment.yaml'
            }
        }
        stage ('Cleanup'){
            steps {
                sh 'mvn clean'
            }
        }
    }
}