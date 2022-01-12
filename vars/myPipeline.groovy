def call(String Manifest_file) {
    pipeline {
	    agent {
          	label '172.27.195.41'
	    }
	    options {
            timeout(time: 3, unit: 'HOURS') 
        }
	    
        stages {	
	        stage('Chekout') {
	     	    steps {
                    cloneCodeManifest(Manifest_file)
	    	    }//steps End	
	        }//stage end
	    	
	        stage('CU_L1_Sanity') {
	        	steps {
	        	    parallel (
                        "ODSC_YOCTO_CU_OID ": {
	        				compileOdsc_CU_Yocto_OID('YOCTO_CU_OID')
	        				compileOdsc_OAMManager_Arm('YOCTO_CU_OID')
	        			},
	        			"x86_CCDU_OID": {
	        			    node(label: 'ODSC_X86_OID_172.27.195.145'){
	        				    cloneCodeManifest(Manifest_file)
                                compileOdsc_CU_x86_CCDU_OID()
	        				    compileOdsc_OAMManager_CCDU()
	        			    }
	        			},
	        			"ODSC_x86_CU_AiO_OID ": {
	        				compileOdsc_CU_x86_AIO_OID('ODSC_x86_CU_AiO_OID')
	        			    compileOdsc_OAMManager_x86('ODSC_x86_CU_AiO_OID')
	        			},
	        			" ODSC_x86_CU_OID ": {
	        			    compileOdsc_CU_x86_OID('ODSC_x86_CU_OID')
	        				compileOdsc_OAMManager_x86('ODSC_x86_CU_OID')
	        			},
	        			"CU-Unit-Test":{
	        			    node(label: 'CU_UT_172.27.195.149'){
	        					cloneCodeManifest(Manifest_file)
	        					compileAndrunCUUt()
	        				}
	        			},
	        			"CU_Kworks":{
	        			    node(label: 'Kworks_24'){
	        				    cloneCodeManifest(Manifest_file)
	        					compileOdsc_CU_klockwork()
	        					genrateArchiveKWorkDiffReport()
	        				}
	        			}
                    ) // parallel End
	        	} // steps End
	        } // stage End	
        } // stages End
        post {
         	always {
                    script{
                        emailextrecipients([[$class: 'CulpritsRecipientProvider'], [$class: 'DevelopersRecipientProvider'],[$class: 'RequesterRecipientProvider']])
                        def emailSubject = "${env.JOB_NAME} - Build# ${env.BUILD_NUMBER} - ${currentBuild.result}"
                        emailext(
                             mimeType: 'text/html',
                             replyTo: 'releasedept@radisys.com',
                             subject: emailSubject,
                             to: "$Author_Email",
                             body: '',
                             compressLog: true,
                             attachLog: true,
                             recipientProviders: [[$class: 'DevelopersRecipientProvider'],[$class: 'RequesterRecipientProvider']]
                        )
                    }
            }//always
	    	cleanup {
	    	    pipelineTeardown()
	    	}
        } //post
    } // pipeline End

}

