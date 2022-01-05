#!/usr/bin/groovy

package Utils

import groovy.util.*
import groovy.xml.*

String changeSrcBranch(String filename, String integrationBranch, String srcBranch) {
    printf('filename : &s', filename)
    printf('tgtBranch : &s', integrationBranch)
    printf('srcBranch : &s', srcBranch)

    def rootNode = new XmlSlurper().parse(filename)
    def myNode = rootNode.depthFirst().find { it.name() == 'project' && it.@revision == integrationBranch}
    myNode.@revision = srcBranch
    def xml = new File(filename)
    xml.withWriter {out-> XmlUtil.serialize(rootNode, out) }
    return myNode.@path
}

return this