def call() {
    homeDir = sh(script: 'printenv HOME', returnStdout: true).trim()
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
