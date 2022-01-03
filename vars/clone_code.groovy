def call(Map config = [:]) {
    sh '''
	    echo "${config.manifestFile}"
	    repo init -u https://jenkins@blrgithub.radisys.com/scm/alm/lte/odsc_manifest.git -m ${config.manifestFile}
		repo sync -j 11 '''
}