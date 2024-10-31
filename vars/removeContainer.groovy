def call(String containerName) {
    sh "docker stop ${containerName} || true"
    sh "docker rm ${containerName} || true"
}
