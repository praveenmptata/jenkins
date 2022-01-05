
import com.Utils

def call(Map Inputs = [:] ) {

    Map default_inputs = [thirdparty:true, clean_workSpace:true, optimize:false]
    Inputs = default_inputs + Inputs
    println "Inputs : ${Inputs.toMapString()}"

    if (! Inputs.containsKey('manifestFile'))
    {
        error("Manifest file not passes as input to clone_code step")
    }

	sh '''git config --global user.name "Jenkins CI Group"
          git config --global user.email "Jenkins.CIGroup@radisys.com"
          git config --global credential.helper store '''

    if (Inputs.clean_workSpace)
    {
        sh ''' cd ${WORKSPACE}; rm -rf * '''
    }
	
    if (Inputs.thirdparty)
    {
        checkout([$class: 'GitSCM', branches: [[name: '*/Arm_roadmap_jenkins_scripts']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: '5g_jobs_thirdparty']], gitTool: 'Default', submoduleCfg: [], userRemoteConfigs: [[credentialsId: '841769bd-6936-4c5e-aa77-5214885738e0', url: 'https://jenkins@blrgithub.radisys.com/scm/alm/lte/5g_jobs_thirdparty.git']]])
    }
	
    if (Inputs.optimize)
    {
        sh "repo init -u https://jenkins@blrgithub.radisys.com/scm/alm/lte/odsc_manifest.git -m ${Inputs.manifestFile} --no-repo-verify --repo-url /opt/git-repo.git"
    }
    else
    {
        sh "repo init -u https://jenkins@blrgithub.radisys.com/scm/alm/lte/odsc_manifest.git -m ${Inputs.manifestFile}"
    }

    def manifestFilePath = "${WORKSPACE}/.repo/manifests/${Inputs.manifestFile}"
    if (! fileExists(manifestFilePath))
    {
        error("repo init failed")
    }

    println "params : ${params.toMapString()}"
    if (params.containsKey('Source_PR_Branch')) {
        sh "sed -i \'s|${params.Dest_PR_Branch}|${params.Source_PR_Branch}|g\' ${manifestFilePath}"
        def output = Utils().runCmdAndGetOutput("cat ${manifestFilePath}")
	    if(! output.contains(params.Source_PR_Branch)) {
            error("${params.Source_PR_Branch} replacement failed in ${manifestFilePath}")
        }
    }

    sh ''' repo sync -j 11 '''
    sh ''' repo info '''
}
