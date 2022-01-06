def call( ) {
    success {
        echo 'cleaning up workspace'
        deleteDir()
    }

    aborted {
        echo 'Cleaning up workspace!'
        deleteDir()
    }

    failure {
        echo 'Cleaning up workspace!'
        deleteDir()
    }
    println "Pipeline termination is completed"
}
