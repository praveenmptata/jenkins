def call (Map pSteps = [:]) {
    m = [:]
    defaultCfg = [status: true, statusFile: 'l2_odsc.txt']
    pSteps = defaultCfg + pSteps

    if(pSteps.status) {
        touch "${WORKSPACE}/${pSteps.statusFile}"
    }

    for( script in pSteps ) {
        if ( script in defaultCfg ) {
		    continue
		}
		def theScript = script
        m[ theScript ] = {
            stage( theScript ) {
                try {
                     pSteps[theScript]()
                     sh "echo ${theScript} SUCCESS >> ${WORKSPACE}/${pSteps.statusFile}"
                }
                catch (Exception e) {
                     sh "echo ${theScript} SUCCESS >> ${WORKSPACE}/${pSteps.statusFile}"
                     throw(e)
                }
            }
        }
    }
    println "Inputs : ${m.toMapString()}"
    parallel m
    sh "cat ${WORKSPACE}/${pSteps.statusFile}"
}