def call (Map pSteps = [:]) {
    m = [:]

    sh " rm ${WORKSPACE}/l2_odsc.txt; touch ${WORKSPACE}/l2_odsc.txt"

    for( name in pSteps.keySet() ) {
		Closure body = pSteps[name]
        m[ name ] = {
		    stage("$name") {
                try {
                     body()
                     sh "echo ${name} SUCCESS >> ${WORKSPACE}/l2_odsc.txt"
                }
                catch (Exception e) {
                     sh "echo ${name} SUCCESS >> ${WORKSPACE}/l2_odsc.txt"
                     throw(e)
                }
			}
        }
    }
    println "Inputs : ${m.toMapString()}"
    parallel m
    sh "cat ${WORKSPACE}/l2_odsc.txt"
}