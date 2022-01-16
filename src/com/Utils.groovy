package com

import java.io.InputStream;
import java.io.FileInputStream
import java.io.File;
import javax.xml.transform.stream.StreamSource
import groovy.util.*
import groovy.xml.*

@NonCPS
boolean reloadJobConfig(String script, String jobname, String folderName) {
    def Ins = hudson.model.Hudson.instance
    def allJobs = Ins.getAllItems(Job.class).findAll { it.getFullName().contains(folderName) && it.name == jobname} 
    if (allJobs.isEmpty()) {
        println 'Error : No Job ${jobname} found inside ${folderName}'
		return false
    }

	for (job in allJobs) {
	    if (job.name == jobname) {
		    println 'job found'
            def configXMLFile = job.getConfigFile()
            def file = configXMLFile.getFile()

            def rootNode = new XmlSlurper().parse(file)
            def myNode = rootNode.depthFirst().find { it.name() == 'script'}
            myNode.replaceBody(script)
            file.withWriter {out-> XmlUtil.serialize(rootNode, out) }

			InputStream is = new FileInputStream(file)
            job.updateByXml(new StreamSource(is));
            job.save();         
        }
    }
	
	return true
}
