import com.Utils

def call(Map Inputs = [:] ) {
    def scriptcontents = libraryResource 'pipeline.txt'
	echo "${scriptcontents}"
	def jobName = "${config.jobName}" 
	def foldername = "${config.folder}"
	if (! new Utils().reloadJobConfig(scriptcontents, jobName, foldername)) {
	    sh 'exit 1'
	}
}
