def call (Map pSteps = [:]) {
    m = [:]

    touch "${WORKSPACE}/l2_odsc.txt"

    for( name in pSteps.keySet() ) {
		Closure body = pSteps[name]
        m[ name ] = stage {
            try {
                 //body()
                 sh "echo ${theScript} SUCCESS >> ${WORKSPACE}/l2_odsc.txt"
            }
            catch (Exception e) {
                 sh "echo ${theScript} SUCCESS >> ${WORKSPACE}/l2_odsc.txt"
                 throw(e)
            }
        }
    }
    println "Inputs : ${m.toMapString()}"
    parallel m
    sh "cat ${WORKSPACE}/l2_odsc.txt"
}