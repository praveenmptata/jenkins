def call (Map pSteps = [:]) {
    m = [:]

    touch "${WORKSPACE}/l2_odsc.txt"

    for( script in pSteps ) {
		def theScript = script
		Closure body = pSteps[theScript]
        m[ theScript ] = {
            stage( theScript ) {
                try {
                     body()
                     sh "echo ${theScript} SUCCESS >> ${WORKSPACE}/l2_odsc.txt"
                }
                catch (Exception e) {
                     sh "echo ${theScript} SUCCESS >> ${WORKSPACE}/l2_odsc.txt"
                     throw(e)
                }
            }
        }
    }
    println "Inputs : ${m.toMapString()}"
    parallel m
    sh "cat ${WORKSPACE}/l2_odsc.txt"
}