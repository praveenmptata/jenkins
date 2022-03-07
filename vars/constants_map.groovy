@groovy.transform.Field
Map repo = [ (constants.manifest_9x)   : ['odsc_manifest.git', '5gran_jio_odsc', '9x'
             (constants.manifest_10x)  : ['odsc_manifest.git', '5gran_jio_odsc', '10x'
             (constants.manifest_ccdu) : ['ccdu_manifest.git', '5gran_jio_ccdu', 'ccdu'] ]
			 .withDefault{ ['odsc_manifest.git', '5gran_jio_odsc', '9x'] }


