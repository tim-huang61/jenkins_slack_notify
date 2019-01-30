def call(String channel, String buildStatus) {
    def status = buildStatus
    def color = ''
    def colorLookup = [
        'STARTED':'good',
        'SUCCESS':'good',
        'FAILURE':'danger',
        'NOT_BUILT':'',
        'UNSTABLE':'danger']

    if(colorLookup.containsKey(status)){
        color = colorLookup[status]
    }
    
    def message = "${env.JOB_NAME} - #${env.BUILD_NUMBER} ${status} after ${env.BUILD_TIMESTAMP} (<${env.BUILD_URL}|#Open>)"
    if(status == 'STARTED'){
        message = "${env.JOB_NAME} - #${env.BUILD_NUMBER} ${env.BUILD_USER} trigger (<${env.BUILD_URL}|#Open>)"    
    }

    slackSend (channel: channel, color: color, message: message)
}
