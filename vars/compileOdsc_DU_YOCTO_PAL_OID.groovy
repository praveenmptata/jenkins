def call(String workpacePath ) {

    sh """
    cd ${workpacePath}
    source /root/sdk/environment-setup-aarch64-poky-linux
    source env_setup
    cd ${workpacePath}/5gran_jio_odsc/ngp/thirdparty/
    rm -rf dpdk/
    cp -rf ${workpacePath}/5g_jobs_thirdparty/dpdk_YOCTO_JIO_ODSC.tar.gz .
    tar -zxvf dpdk.tar.gz
    cd ${workpacePath}/5gran_jio_odsc/ngp/build
    make TARGET=arm -j 20"""

    if (! fileExists("${workpacePath}/5gran_jio_odsc/ngp/build/libngp.a"))
    {
        echo "NGP compilation failed"
        sh 'exit 1'
    }
    println 'NGP compilation is done'

    sh """
    cd ${workpacePath}/5gran_jio_odsc/5gran/du/build/pal
    ./build_du_arm_odsc.sh FH_DPDK=YES MH_DPDK=YES """

    if (fileExists("${workpacePath}/5gran_jio_odsc/5gran/du/build/pal/du_bin/bin/gnb_du")) 
    {
        echo "***** gnb_du binary is generated*****"
    }
    else
    {
        echo "gnb_du is not generted"
        sh 'exit 1'
    }
}
