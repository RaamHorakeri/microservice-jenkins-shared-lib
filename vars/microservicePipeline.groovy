// microservice-jenkins-shared-lib/vars/microservicePipeline.groovy

def call(String branch, String gitUrl, String imageName, String containerName, String portMapping) {
    // Directly define the stages without wrapping them in a pipeline block
    stages {
        stage('Checkout from Git') {
            steps {
                script {
                    git branch: branch, url: gitUrl
                }
            }
        }
        stage("Docker Build") {
            steps {
                script {
                    sh "docker build -t ${imageName}:latest ."
                }
            }
        }
        stage("Remove container") {
            steps {
                script {
                    sh "docker stop ${containerName} || true"
                    sh "docker rm ${containerName} || true"
                }
            }
        }
        stage('Deploy to container') {
            steps {
                script {
                    sh "docker run -d --name ${containerName} -p ${portMapping} ${imageName}:latest"
                }
            }
        }
    }
}
