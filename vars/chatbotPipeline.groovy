def call() {
    pipeline {
        agent any
        stages {
            stage('Checkout from Git') {
                steps {
                    git branch: 'legacy', url: 'https://github.com/Aj7Ay/chatbot-ui.git'
                }
            }
            stage("Docker Build") {
                steps {
                    script {
                        sh "docker build -t chatbot:latest ."
                    }
                }
            }
            stage("Remove container") {
                steps {
                    sh "docker stop chatbot || true"
                    sh "docker rm chatbot || true"
                }
            }
            stage('Deploy to container') {
                steps {
                    sh 'docker run -d --name chatbot -p 3000:3000 chatbot:latest'
                }
            }
        }
    }
}
