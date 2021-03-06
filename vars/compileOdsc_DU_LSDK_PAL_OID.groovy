def call(String workpacePath = ${WORKSPACE}) {

    println "running code inside : ${workpacePath}"
    dir("$workpacePath") {

        copyCodetoWs()

        sh """
        source env_setup
        cd 5gran_jio_odsc/ngp/thirdparty/
        rm -rf dpdk
        cp -rf ${WORKSPACE}/5g_jobs_thirdparty/dpdk.tar.gz .
        tar -zxvf dpdk.tar.gz
        cd 5gran_jio_odsc/ngp/build
        make TARGET=arm CRYPTO=YES -j 20"""

        if (! fileExists("5gran_jio_odsc/ngp/build/libngp.a"))
        {
            echo "NGP compilation failed"
            sh 'exit 1'
        }
        println 'NGP compilation is done'

        sh """
        cd 5gran_jio_odsc/5gran/du/build/pal
        ./build_du_arm_odsc.sh FH_DPDK=YES MH_DPDK=YES """

        if (fileExists("5gran_jio_odsc/5gran/du/build/pal/du_bin/bin/gnb_du"))
        {
            echo "***** gnb_du binary is generated*****"
        }
        else
        {
            echo "gnb_du is not generted"
            sh 'exit 1'
        }
    }
}
