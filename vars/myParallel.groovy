def call (Map pSteps = [:]) {
    m = [:]
    defaultCfg = [statusFile: 'l2_odsc.txt']
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
                     sh "echo ${myName} SUCCESS >> ${WORKSPACE}/l2_odsc.txt"
                }
                catch (Exception e) {
                     sh "echo ${myName} SUCCESS >> ${WORKSPACE}/l2_odsc.txt"
                     throw(e)
                }
			}
        }
    }
    println "Inputs : ${m.toMapString()}"
    parallel m
    sh "cat ${WORKSPACE}/l2_odsc.txt"
}