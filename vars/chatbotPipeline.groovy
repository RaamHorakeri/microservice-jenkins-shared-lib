// vars/chatbotPipeline.groovy
def call() {
    // Placeholder to avoid calling this method directly in the pipeline
}

def checkoutFromGit() {
    // Checkout from Git repository
    git branch: 'legacy', url: 'https://github.com/RaamHorakeri/chatbot-ui.git'
}

def buildDockerImage() {
    // Build the Docker image
    sh "docker build -t chatbot:latest ."
}

def removeContainer() {
    // Stop and remove the existing container
    sh "docker stop chatbot || true"
    sh "docker rm chatbot || true"
}

def deployToContainer() {
    // Run the Docker container
    sh 'docker run -d --name chatbot -p 3000:3000 chatbot:latest'
}
