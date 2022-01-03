def call(Map config = [:]) {
    sh '''git config --global user.name "Jenkins CI Group" 
	      git config --global user.email "Jenkins.CIGroup@radisys.com" 
          git config --global credential.helper store '''
	
	if (config.clean_workSpace == true)
	{
	    sh ''' cd ${WORKSPACE}; rm -rf * '''
	}
	
	if (config.optimize == true)
	{
	    echo "optimization is enabled"
	}
	else
	{
        sh "repo init -u https://jenkins@blrgithub.radisys.com/scm/alm/lte/odsc_manifest.git -m ${config.manifestFile}"
	    sh ''' repo sync -j 11 '''
	}
}