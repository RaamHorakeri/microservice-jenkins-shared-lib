// microservicePipeline.groovy

def call(String branch, String gitUrl, String imageName, String containerName, String portMapping) {
    pipeline {
        agent any
        stages {
            stage('Checkout from Git') {
                steps {
                    git branch: branch, url: gitUrl
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
                    sh "docker stop ${containerName} || true"
                    sh "docker rm ${containerName} || true"
                }
            }
            stage('Deploy to container') {
                steps {
                    sh "docker run -d --name ${containerName} -p ${portMapping} ${imageName}:latest"
                }
            }
        }
    }
}
