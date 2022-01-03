def call(Map config = [:]) {
    sh '''
	    repo init -u https://jenkins@blrgithub.radisys.com/scm/alm/lte/odsc_manifest.git -m $config.manifestFile
		repo sync -j 11 '''
}