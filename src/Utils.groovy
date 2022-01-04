package Utils

import groovy.util.*


String getCodePath(String filename, String intBranch) {
    def rootNode = new XmlSlurper().parse(filename)
	def myNode = rootNode.depthFirst().findAll { it.name() == 'project' && it.revision == intBranch}
	return myNode.path.text()
}

return this