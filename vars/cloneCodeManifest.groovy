def call(Map Inputs = [:] ) {

    Map default_inputs = [clean_workSpace:true, optimize:false]
    Inputs = default_inputs + Inputs
    println "Inputs : ${Inputs.toMapString()}"

    if (! Inputs.containsKey('manifestFile')) {
        error("Manifest file not passes as input to clone_code step")
    }

    sh ( script : '''git config --global user.name "Jenkins CI Group"
        git config --global user.email "Jenkins.CIGroup@radisys.com"
        git config --global credential.helper store ''', label : 'set the clone credentials')

    if (Inputs.clean_workSpace) {
        sh ''' cd ${WORKSPACE}; rm -rf * ; rm -rf .repo'''
    }
	
    if (Inputs.optimize) {
        sh "repo init -u https://jenkins@blrgithub.radisys.com/scm/alm/lte/odsc_manifest.git -m ${Inputs.manifestFile} --no-repo-verify --repo-url /opt/git-repo.git"
    }
    else {
        sh "repo init -u https://jenkins@blrgithub.radisys.com/scm/alm/lte/odsc_manifest.git -m ${Inputs.manifestFile}"
    }

    def manifestFilePath = "${WORKSPACE}/.repo/manifests/${Inputs.manifestFile}"
    if (! fileExists(manifestFilePath)) {
        error("repo init failed")
    }

    env.build = Inputs.manifestFile

    println "params : ${params.toMapString()}"
    if (params.containsKey('Source_PR_Branch')) {
        sh "sed -i \'/${params.repo}/ s|${params.Dest_PR_Branch}|${params.Source_PR_Branch}|g\' ${manifestFilePath}"
    }
    println "Environment Variables : ${env.build}"
    sh ''' repo sync -j 11 '''
    sh ''' repo info '''
	
}
