def call(String config = 'hello') {
    sh "echo ${config}"
	dir('cool') {
	    copyCodetoWs()
		sh '''#!/usr/bin/bash
		source env_setup'''
	}
}