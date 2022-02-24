def call(String manifestFile) {
    Map repo = [ (constants.manifest_9x)   : '9x',
                 (constants.manifest_10x)  : '10x',
                 (constants.manifest_ccdu) : '9x']
  
    return repo[manifestFile]

}