def call(String buildStatus) {
    def status = buildStatus ?: 'SUCCESS'
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
    
    def message = "${env.JOB_NAME} - #${env.BUILD_NUMBER} ${status} after ${env.BUILD_USER_ID} (<${env.BUILD_URL}|#Open>)"
    if(status == 'STARTED'){
        message = "${env.JOB_NAME} - #${env.BUILD_NUMBER} ${env.BUILD_USER_ID} trigger (<${env.BUILD_URL}|#Open>)"    
    }

    slackSend (channel: 'test', color: color, message: message)
}
