def call(int build) {
    pipeline {
        agent any
	    
        stages {
		    if (build %2 != 0) {
                stage('checkout') {
                    steps {
                        echo "${WORKSPACE}"
                        cloneCodeManifest(manifestFile:constants.manifest_file_9x)
                    }
                }
			}
			else {
                stage('sample') {
				    echo 'sample stage'
				}
            }
        }
        
        post {
            cleanup{
                pipelineTeardown()
            }
        }
    }
}
