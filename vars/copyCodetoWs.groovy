def call() {

    sh'''
    cp -rf ${WORKSPACE}/5gran_jio_odsc/ .
    cp -rf ${WORKSPACE}/repo .
    cp -rf ${WORKSPACE}/env_setup .'''
}
