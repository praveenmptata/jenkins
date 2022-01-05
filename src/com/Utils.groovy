#!/usr/bin/groovy

package com

import groovy.util.*
import groovy.xml.*

String changeSrcBranch(String filename, String integrationBranch, String srcBranch) {
    printf('filename :  %s', filename)
    printf('tgtBranch : %s', integrationBranch)
    printf('srcBranch : %s', srcBranch)

    def xml = new File(filename)
	println 'File is able to open'
    def rootNode = new XmlSlurper().parse(File(filename))
    def myNode = rootNode.depthFirst().find { it.name() == 'project' && it.@revision == integrationBranch}
    myNode.@revision = srcBranch
    println 'read is done'
    xml.withWriter {out-> XmlUtil.serialize(rootNode, out) }
    return myNode.@path
}

return this