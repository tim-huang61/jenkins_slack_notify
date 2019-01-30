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
    
    def user = 'unknow'
    wrap([$class: 'BuildUser']) {
        user = "${BUILD_USER}"
    }

    def message = "${env.JOB_NAME} - #${env.BUILD_NUMBER} ${status} after ${currentBuild.durationString} (<${env.BUILD_URL}|#Open>)"
    if(status == 'STARTED'){
        message = "${env.JOB_NAME} - #${env.BUILD_NUMBER} ${user} trigger (<${env.BUILD_URL}|#Open>)"    
    }

    slackSend (channel: 'test', color: color, message: message)
}
