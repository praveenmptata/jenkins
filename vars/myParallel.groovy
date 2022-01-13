def call (Map pSteps = [:]) {
    m = [:]
    defaultCfg = [status: true, statusFile: 'l2_odsc.txt']
	pSteps = defaultCfg + pSteps
    sh " rm ${WORKSPACE}/l2_odsc.txt; touch ${WORKSPACE}/l2_odsc.txt"

    for( name in pSteps.keySet() ) {
	
		if (name in defaultCfg.keySet()) {
		    continue
		}
		
	    def myName = name
		Closure body = pSteps[name]
        m[ myName ] = {
		    script {
                try {
                    body()
					if(pSteps.status) {
                        sh "echo ${myName} SUCCESS >> ${WORKSPACE}/${pSteps.statusFile}"
					}
                }
                catch (Exception e) {
				    if(pSteps.status) {
                        sh "echo ${myName} SUCCESS >> ${WORKSPACE}/${pSteps.statusFile}"
					}
                     throw(e)
                }
			}
        }
    }
    println "Inputs : ${m.toMapString()}"
    parallel m
    sh "cat ${WORKSPACE}/${pSteps.statusFile}"
}