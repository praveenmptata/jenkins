import com.Utils

def call(Map Inputs = [:] ) {
    def scriptcontents = libraryResource 'pipeline.txt'
	def ret = new Utils().reloadJobConfig(scriptcontents, "${Inputs.jobName}" , "${Inputs.folder}")
	println ret
	if (! ret) {
	    sh 'exit 1'
	}
}
