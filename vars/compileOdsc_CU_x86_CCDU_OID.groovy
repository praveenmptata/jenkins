
def call(String workpacePath ) {

    sh """
    cd ${workpacePath}
    source env_setup

    cd ${workpacePath}/5gran_jio_odsc/ngp/thirdparty/dpdk
    cp -rf ${workpacePath}/5g_jobs_thirdparty/dpdk-20.11.tar.gz .
    tar -zxvf dpdk-20.11.tar.gz
    cd ${workpacePath}/5gran_jio_odsc/ngp/build/
    make clean; make DPDK_VER=20.11 CRYPTO=YES -j 5"""

    if (! fileExists("${workpacePath}/5gran_jio_odsc/ngp/build/libngp.a"))
    {
        echo "NGP compilation failed"
        sh 'exit 1'
    }
    println 'NGP compilation is done'

    sh """
    cd ${workpacePath}/5gran_jio_odsc/5gran/cu/build/
    sed -i 's/UEXPLICIT_MEM_ALLOCATOR_FOR_STL/DEXPLICIT_MEM_ALLOCATOR_FOR_STL/g' flags.mk
    make clean; make LIBOAM=OID PRODUCT=MACRO DPDK_VER=20.11 CRYPTO=YES -j 5"""

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
