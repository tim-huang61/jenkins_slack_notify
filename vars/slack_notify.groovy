def call(String channel, String buildStatus) {
    def status = buildStatus ?: 'SUCCESS'
    def color = ''
    def colorLookup = [
        'STARTED':'',
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

    def job = "${env.JOB_NAME} - #${env.BUILD_NUMBER}"
    def normal_msg = " ${status} after ${currentBuild.durationString} (<${env.BUILD_URL}|#Open>)"
    def start_msg = " ${user} trigger (<${env.BUILD_URL}|#Open>)"
    def message = job + status == 'STARTED' ? start_msg : normal_msg
   
    slackSend (channel, color, message)
}
