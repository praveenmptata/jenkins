def call() {
    pipeline {
        agent { label "132_agent" }
	    
        stages {
            stage('checkout') {
                steps {
                    echo "${WORKSPACE}"
                    cloneCodeManifest(manifestFile:constants.manifest_file_9x)
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
