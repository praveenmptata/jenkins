def call(String workpacePath = ${WORKSPACE}) {

    println "running code inside : ${workpacePath}"
    dir("$workpacePath") {

        copyCodetoWs()

        sh """
        cd ${workpacePath}
        source env_setup

        source /root/sdk/environment-setup-aarch64-poky-linux
        cd ${workpacePath}/5gran_jio_odsc/ngp/thirdparty/
        rm -rf dpdk
        cp -rf ${workpacePath}/5g_jobs_thirdparty/dpdk_YOCTO_JIO_ODSC.tar.gz .
        tar -zxvf dpdk_YOCTO_JIO_ODSC.tar.gz

        cd ${workpacePath}/5gran_jio_odsc/ngp/build
        make TARGET=arm -j 20"""

        if (! fileExists("${workpacePath}/5gran_jio_odsc/ngp/build/libngp.a"))
        {
            echo "NGP compilation failed"
            sh 'exit 1'
        }
        println 'NGP compilation is done'

        sh '''
        cd ${workpacePath}/5gran_jio_odsc/5gran/cu/build/
        ./build_cu_arm.sh '''

        if (fileExists("${workpacePath}/5gran_jio_odsc/5gran/cu/build/cu_bin/bin/gnb_cu")) 
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
