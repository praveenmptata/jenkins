import com.Utils

def call(Map Inputs = [:] ) {
    def scriptcontents = libraryResource 'pipeline.txt'
	if (! new Utils().reloadJobConfig(scriptcontents, "${Inputs.jobName}" , "${Inputs.folder}")) {
	    return 0
	}
}
