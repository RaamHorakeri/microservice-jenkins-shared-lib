def call() {
    stages {
        stage('Checkout from Git') {
            steps {
                script {
                    checkoutFromGit()
                }
            }
        }
        stage("Docker Build") {
            steps {
                script {
                    buildDockerImage()
                }
            }
        }
        stage("Remove Container") {
            steps {
                script {
                    removeContainer()
                }
            }
        }
        stage('Deploy to Container') {
            steps {
                script {
                    deployToContainer()
                }
            }
        }
    }
}

def checkoutFromGit() {
    git branch: 'legacy', url: 'https://github.com/Aj7Ay/chatbot-ui.git'
}

def buildDockerImage() {
    sh "docker build -t chatbot:latest ."
}

def removeContainer() {
    sh "docker stop chatbot || true"
    sh "docker rm chatbot || true"
}

def deployToContainer() {
    sh 'docker run -d --name chatbot -p 3000:3000 chatbot:latest'
}
