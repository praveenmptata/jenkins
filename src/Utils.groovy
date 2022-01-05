package Utils

import groovy.util.*


String getCodePath(String filename, String integrationBranch) {
    def rootNode = new XmlSlurper().parse(filename)
	def myNode = rootNode.depthFirst().find { it.name() == 'project' && it.@revision == integrationBranch}
	return myNode.@path
}

return this