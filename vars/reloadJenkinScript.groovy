import com.Utils

def call(String Job Inputs = [:] ) {
    def scriptcontents = libraryResource 'pipeline.txt'
	if (! new Utils().reloadJobConfig(scriptcontents, "${Inputs.jobName}")) {
	    println "Setting job as failed" 
	    currentBuild.result = 'FAILURE'
	}
}
