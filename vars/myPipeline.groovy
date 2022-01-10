def call(Map Inputs = [:] ) {

    // evaluate the body block, and collect configuration into the object
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

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
