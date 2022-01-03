def call(Map config = [thirdparty:false, clean_workSpace:true, optimize:false]) {
    sh '''git config --global user.name "Jenkins CI Group"
          git config --global user.email "Jenkins.CIGroup@radisys.com"
          git config --global credential.helper store '''
	
	if (config.clean_workSpace == true)
	{
	    sh ''' cd ${WORKSPACE}; rm -rf * '''
	}
	
	if (config.thirdparty == true)
	{
	    checkout([$class: 'GitSCM', branches: [[name: '*/Arm_roadmap_jenkins_scripts']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: '5g_jobs_thirdparty']], gitTool: 'Default', submoduleCfg: [], userRemoteConfigs: [[credentialsId: '841769bd-6936-4c5e-aa77-5214885738e0', url: 'https://jenkins@blrgithub.radisys.com/scm/alm/lte/5g_jobs_thirdparty.git']]])
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