def call() {
    homeDir = sh(script: 'printenv HOME', returnStdout: true).trim()
	def i = getFirstAwailableWs()
	try {
	    sh 'exit 1'
	} catch (error) {
	    sh "rm ${homeDir}/lockWs${i}"
		throw error
	}
	getFirstAwailableWs()
}

def getFirstAwailableWs() {
    for( lock in ['lockWs0', 'lockWs1', 'lockWs2']) {
        if(! fileExists("${homeDir}/${lock}")) {
            sh "touch ${homeDir}/${lock}"
            return lock[-1]
        }
    }
}
