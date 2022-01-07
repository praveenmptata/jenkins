def call(String workpacePath = ${WORKSPACE}) {

    println "running code inside : ${workpacePath}"
    dir("$workpacePath") {

        copyCodetoWs()

        sh """
        source env_setup
        cd 5gran_jio_odsc/ngp/build
        sed -i 's/-DFAST_CRYPTO_ENABLED/-UFAST_CRYPTO_ENABLED/g' flags.mk
        make TARGET=arm -j 20"""

        if (! fileExists("5gran_jio_odsc/ngp/build/libngp.a"))
        {
            echo "NGP compilation failed"
            sh 'exit 1'
        }
        println 'NGP compilation is done'

        sh """
        cd 5gran_jio_odsc/5gran/du/build/du_ut
        sed -i 's/YANG/JSON/g' build_du.sh
        sed -i 's/NS=YES/NS=NO/g' build_du.sh
        ./build_du.sh"""

        if (fileExists("5gran_jio_odsc/5gran/du/build/du_ut/du_bin/bin/gnb_du"))
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
