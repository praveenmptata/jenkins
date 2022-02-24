def call(String manifestFile) {
    Map repo = [ (constants.manifest_9x)   : '9x',
                 (constants.manifest_10x)  : '10x']
  
    return repo[manifestFile]

}