def call(String workpacePath = ${WORKSPACE}) {

    println "running code inside : ${workpacePath}"
    dir("$workpacePath") {

        copyCodetoWs()

        sh """
        cd ${workpacePath}
        source env_setup"""

        if (! fileExists("${workpacePath}/5gran_jio_odsc/ngp/build/libngp.a"))
        {
            sh """
            cd ${workpacePath}/5gran_jio_odsc/ngp/build
            make -j 5"""
        }
        println 'NGP compilation is done'

        sh """
        cd ${workpacePath}/5gran_jio_odsc/5gran/cu/build/
        ./build_x86_odsc.sh.sh """

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
}
