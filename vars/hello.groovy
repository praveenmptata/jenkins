def call() {
    homeDir = sh(script: 'printenv HOME', returnStdout: true).trim()
	getFirstAwailableWs()
}

def getFirstAwailableWs() {
    ['lockWs0', 'lockWs1', 'lockWs2'].eachWithIndex {it, index ->
        if(! fileExists("${homeDir}/${it}")) {
            sh "touch ${homeDir}/${it}"
            return index
        }
    }
}
