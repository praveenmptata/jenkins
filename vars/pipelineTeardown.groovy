def call(Map Config = [:] ) {

    default_inp = ['onSuccess': true, 'onFailure': true, 'onAbort': true]
    Config = default_inp + Config
    println "Inputs : ${Config.toMapString()}"
    println "Build Status : currentBuild.currentResult"

    success {
        if (Config.onSuccess) {
            echo 'cleaning up workspace'
            deleteDir()
        }
    }

    aborted {
        if (Config.onAbort) {
            echo 'Cleaning up workspace!'
            deleteDir()
        }
    }

    failure {
        if (Config.onFailure) {
            echo 'Cleaning up workspace!'
            deleteDir()
        }
    }

    println "Pipeline termination is completed"
}
