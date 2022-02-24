def call(String manifestFile) {
    Map repo = [ (constants.manifest_file_9x)   : '9x',
                 (constants.manifest_file_10x)  : '10x']
  
    return repo[manifestFile]

}