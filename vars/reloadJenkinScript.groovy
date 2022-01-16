import com.Utils

def call(Map Inputs = [:] ) {
    def scriptcontents = libraryResource 'pipeline.txt'
	def jobName = "${Inputs.jobName}" 
	def foldername = "${Inputs.folder}"
	if (! new Utils().reloadJobConfig(scriptcontents, jobName, foldername)) {
	    sh 'exit 1'
	}
}
