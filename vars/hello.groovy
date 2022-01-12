def call(String config = 'hello') {
    sh "echo ${config}"
	dir('cool') {
	    copyCodetoWs()
	}
}