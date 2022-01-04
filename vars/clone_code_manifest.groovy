def call(Map Inputs = [thirdparty:true, clean_workSpace:true, optimize:false]) {

    println "Inputs : ${Inputs.toMapString()}"

    if (! Inputs.containsKey('manifestFile'))
	{
	    // TODO : Make sure Build status is marked as fail.
		this.hello('FAILED')
	    error("Manifest file not passes as input to clone_code step")
	}

    // TODO : Make sure Build status is marked as In-Progress.
	this.hello('INPROGRESS')
	sh '''git config --global user.name "Jenkins CI Group"
          git config --global user.email "Jenkins.CIGroup@radisys.com"
          git config --global credential.helper store '''

	if (Inputs.clean_workSpace == true)
	{
	    sh ''' cd ${WORKSPACE}; rm -rf * '''
	}
	
	if (Inputs.thirdparty == true)
	{
        checkout([$class: 'GitSCM', branches: [[name: '*/Arm_roadmap_jenkins_scripts']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: '5g_jobs_thirdparty']], gitTool: 'Default', submoduleCfg: [], userRemoteConfigs: [[credentialsId: '841769bd-6936-4c5e-aa77-5214885738e0', url: 'https://jenkins@blrgithub.radisys.com/scm/alm/lte/5g_jobs_thirdparty.git']]])
	}
	
	if (Inputs.optimize == true)
	{
        sh "repo init -u https://jenkins@blrgithub.radisys.com/scm/alm/lte/odsc_manifest.git -m ${Inputs.manifestFile} --no-repo-verify --repo-url /opt/git-repo.git"
	}
	else
	{
        sh "repo init -u https://jenkins@blrgithub.radisys.com/scm/alm/lte/odsc_manifest.git -m ${Inputs.manifestFile}"
	}
	
	sh ''' repo sync -j 11 '''
	
}

def hello(String Msg) {
    echo "${Msg}"
}

def notifyStash(String state) {
    if('SUCCESS' == state || 'FAILED' == state) {
        currentBuild.result = state         // Set result of currentBuild !Important!
    }
    step([$class: 'StashNotifier',
          credentialsId: '841769bd-6936-4c5e-aa77-5214885738e0',
          disableInprogressNotification: false,
          ignoreUnverifiedSSLPeer: true,
          includeBuildNumberInKey: false,
          prependParentProjectKey: true,
          projectKey: '',
          stashServerBaseUrl: 'https://alm.radisys.com/bitbucket'
	])
}