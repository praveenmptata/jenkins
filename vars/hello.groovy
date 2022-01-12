def call(String config = 'hello') {
    sh "echo ${config}"
	dir('cool') {
	    copyCodetoWs()
		sh 'pwd
		ls -lrt
		source env_setup'
	}
}