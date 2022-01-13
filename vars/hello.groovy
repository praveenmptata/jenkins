def call(String config = 'hello') {
    sh "echo ${config}"
	dir('cool') {
	    copyCodetoWs()
		sh '''#!/bin/bash
		source env_setup'''
		dir ('5gran_jio_odsc/ngp/thirdparty/dpdk') {
		    sh 'pwd'
		}
		dir ('5gran_jio_odsc/5gran/cu/build/') {
		    sh 'pwd'
		}
	}
}