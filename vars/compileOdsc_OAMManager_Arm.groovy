
def call(String workpacePath ) {

    if (! fileExists("${workpacePath}/5gran_jio_odsc/ngp/build/libngp.a"))
    {
        sh """
        cd ${workpacePath}/5gran_jio_odsc/ngp/thirdparty/dpdk
        cp -rf ${workpacePath}/5g_jobs_thirdparty/dpdk.tar.gz .
        tar -zxvf dpdk.tar.gz
        cd ${workpacePath}/5gran_jio_odsc/ngp/build/
        make TARGET=arm -j 20"""
    }

    sh """
    cd ${workpacePath}
    ./build_arm_odsc.sh"""

    if (fileExists("${workpacePath}/5gran_jio_odsc/5gran/oam/oid_oam_manager/build/oid_bin/bin/oid_oam_manager")) 
    {
        echo "***** OID binary is generated*****"
    }
    else
    {
        echo "OID binary is not generted"
        sh 'exit 1'
    }
}
