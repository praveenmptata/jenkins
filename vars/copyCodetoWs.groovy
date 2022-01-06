def call() {
    def path = sh(script: 'pwd', returnStdout: true).trim()
	if (path != "${WORKSPACE}")
    {
        sh'''
        cp -rf ${WORKSPACE}/5gran_jio_odsc/ .
        cp -rf ${WORKSPACE}/repo .
        cp -rf ${WORKSPACE}/env_setup .'''
    }
}
