def call(String channel, String buildStatus) {
    def status = buildStatus ?: 'SUCCESS'
    def user = 'unknown'
    def colorLookup = [
        'STARTED':'',
        'SUCCESS':'good',
        'FAILURE':'danger',
        'NOT_BUILT':'',
        'UNSTABLE':'danger']

    def color = colorLookup.containsKey(status) ? colorLookup[status] : '';
    wrap([$class: 'BuildUser']) {
       user = "${BUILD_USER}"
    }

    def job = "${env.JOB_NAME} - #${env.BUILD_NUMBER} "
    def normal_msg = "${status} after ${currentBuild.durationString}. (<${env.BUILD_URL}|#Open>)"
    def start_msg = "${user} triggered this job. (<${env.BUILD_URL}|#Open>)"
    def message = job + status == 'STARTED' ? start_msg : normal_msg
   
    slackSend (channel: 'test', color: color, message: message)
}
