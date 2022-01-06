def call(Map Config = [:] ) {

    clenup = ['SUCCESS': true, 'FAILURE': true, 'ABORTED': true]
    Config = clenup + Config
    println "Inputs : ${Config.toMapString()}"
    println "Build Status : currentBuild.currentResult"

    if (Config["${currentBuild.currentResult}"]) {
            echo 'cleaning up workspace'
            deleteDir()
    }

    println "Pipeline termination is completed"
}
