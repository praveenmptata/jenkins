def call(String config = 'hello') {
    sh "echo ${config}"
	dir('cool') {
	    copyCodetoWs()
		sh '''#!/bin/bash
		source env_setup'''
	}
}