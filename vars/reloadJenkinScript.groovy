import com.Utils

def call(Map Inputs = [:] ) {
    def scriptcontents = libraryResource 'pipeline.txt'
	if (! new Utils().reloadJobConfig(scriptcontents, "${Inputs.jobName}" , "${Inputs.folder}")) {
	    println "Setting job as failed" 
	    currentBuild.result = 'FAILURE'
	}
}
