def call(String imageName, String containerName, int port, String additionalParams = '') {
    sh "docker run -d --name ${containerName} -p ${port}:${port} ${additionalParams} ${imageName}:latest"
}
