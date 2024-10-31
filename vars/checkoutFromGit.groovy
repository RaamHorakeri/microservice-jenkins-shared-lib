def call(String branch, String repoUrl) {
    git branch: branch, url: repoUrl
}
