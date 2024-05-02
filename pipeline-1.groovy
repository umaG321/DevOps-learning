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
        stage ('Deploy too QA') {
            steps {
                sh 'kubectl apply -f qa-deployment.ymal'
            }
        }
        stage ('')
        stage ('deploy'){
            steps {
                echo "deploy to Kubernetes"
            }
        }
    }
}