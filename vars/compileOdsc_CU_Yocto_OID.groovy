def call(String workpacePath = ${WORKSPACE}) {

    println "running code inside : ${workpacePath}"
    dir("$workpacePath") {

        copyCodetoWs()

        sh """
        source env_setup

        source /root/sdk/environment-setup-aarch64-poky-linux
        cd 5gran_jio_odsc/ngp/thirdparty/
        rm -rf dpdk
        cp -rf ${WORKSPACE}/5g_jobs_thirdparty/dpdk_YOCTO_JIO_ODSC.tar.gz .
        tar -zxvf dpdk_YOCTO_JIO_ODSC.tar.gz

        cd 5gran_jio_odsc/ngp/build
        make TARGET=arm -j 20"""

        if (! fileExists("5gran_jio_odsc/ngp/build/libngp.a"))
        {
            echo "NGP compilation failed"
            sh 'exit 1'
        }
        println 'NGP compilation is done'

        sh '''
        cd 5gran_jio_odsc/5gran/cu/build/
        ./build_cu_arm.sh '''

        if (fileExists("5gran_jio_odsc/5gran/cu/build/cu_bin/bin/gnb_cu"))
        {
            echo "***** gnb_cu binary is generated*****"
        }
        else
        {
            echo "gnb_cu is not generted"
            sh 'exit 1'
        }
    }
}
