
def call(String workpacePath ) {

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
