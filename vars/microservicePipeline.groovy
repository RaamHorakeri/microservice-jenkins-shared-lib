// vars/microservicePipeline.groovy

def call(String branch, String gitUrl, String imageName, String containerName, String portMapping) {
    pipeline {
        agent any
        stages {
            stage('Checkout from Git') {
                steps {
                    script {
                        env.GIT_BRANCH = branch  // Set the branch as an environment variable
                        env.GIT_URL = gitUrl     // Set the Git URL as an environment variable
                        git branch: env.GIT_BRANCH, url: env.GIT_URL
                    }
                }
            }
            stage("Docker Build") {
                steps {
                    script {
                        env.IMAGE_NAME = imageName  // Set the image name as an environment variable
                        sh "docker build -t ${env.IMAGE_NAME}:latest ."
                    }
                }
            }
            stage("Remove container") {
                steps {
                    script {
                        env.CONTAINER_NAME = containerName  // Set the container name as an environment variable
                        sh "docker stop ${env.CONTAINER_NAME} || true"
                        sh "docker rm ${env.CONTAINER_NAME} || true"
                    }
                }
            }
            stage('Deploy to container') {
                steps {
                    script {
                        env.PORT_MAPPING = portMapping  // Set the port mapping as an environment variable
                        sh "docker run -d --name ${env.CONTAINER_NAME} -p ${env.PORT_MAPPING} ${env.IMAGE_NAME}:latest"
                    }
                }
            }
        }
    }
}
