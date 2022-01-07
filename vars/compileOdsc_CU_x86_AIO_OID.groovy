def call(String workpacePath = ${WORKSPACE}) {

    println "running code inside : ${workpacePath}"
    dir("$workpacePath") {

        copyCodetoWs()

        sh """
        source env_setup
        cd 5gran_jio_odsc/ngp/build/
        sed -i 's/-DFAST_CRYPTO_ENABLED/-UFAST_CRYPTO_ENABLED/g' flags.mk
        cat flags.mk
        make -j 5"""

        if (! fileExists("5gran_jio_odsc/ngp/build/libngp.a"))
        {
            echo "NGP compilation failed"
            sh 'exit 1'
        }
        println 'NGP compilation is done'

        sh """
        cd 5gran_jio_odsc/5gran/cu/build/
        sed -i 's/-UEXPLICIT_MEM_ALLOCATOR_FOR_STL/-DEXPLICIT_MEM_ALLOCATOR_FOR_STL/g' flags.mk
        sed -i 's/-DE1_PRIME_SPLIT_ENABLED=0/-DE1_PRIME_SPLIT_ENABLED=1/g' flags.mk;
        sed -i 's/-DFAST_PKT_UPPER/-UFAST_PKT_UPPER/g' flags.mk
        sed -i 's/-DFAST_PKT_LOWER/-UFAST_PKT_LOWER/g' flags.mk
        cat flags.mk
        make LIBOAM=OID PRODUCT=ODSC -j 5 """

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
