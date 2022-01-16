
package com

import java.io.InputStream;
import java.io.FileInputStream
import java.io.File;
import javax.xml.transform.stream.StreamSource
import groovy.util.*
import groovy.xml.*

@NonCPS
boolean reloadJobConfig(String script, String toBeCopiedJobName, String folderName) {
    println 'hello'
    def allJobs = hudson.model.Hudson.instance.getAllItems(Job.class).findAll { it.getFullName().contains(folderName) && it.name == toBeCopiedJobName} 
    if (allJobs.isEmpty()) {
        println 'Error : No Job ${toBeCopiedJobName} found inside ${folderName}'
		return false
    }

    println "script : ${script}"

	for (job in allJobs) {
	    if (job.name == toBeCopiedJobName) {
		    println 'job found'
            def configXMLFile = job.getConfigFile()
            def file = configXMLFile.getFile()

            def rootNode = new XmlSlurper().parse(file)
            def myNode = rootNode.depthFirst().find { it.name() == 'script'}
            myNode.text = script
            def xml = new File(file)
            xml.withWriter {out-> XmlUtil.serialize(rootNode, out) }

			InputStream is = new FileInputStream(file)
            job.updateByXml(new StreamSource(is));
            job.save();         
        }
    }
	
	return true
}
