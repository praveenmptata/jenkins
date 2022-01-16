
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
	println "jobname : ${toBeCopiedJobName}"
	println "folder : ${folderName}"
	for (job in allJobs) {
	    if (job.name == toBeCopiedJobName) {
		    println 'job found'
            def configXMLFile = job.getConfigFile()
            def file = configXMLFile.getFile()
            
            InputStream is = new FileInputStream(file)
            println 'got the file'
            def rootNode = new XmlSlurper().parse(is)
            def myNode = rootNode.depthFirst().find { it.name() == 'script'}
            myNode.text = script
			println 'updated the script'
            def xml = new File("/${System.getProperty('user.home')}/temp.xml")
			println "Initialise your file : /${System.getProperty('user.home')}/temp.xml"
			xml.createNewFile()
			println 'file created'
            xml.withWriter {out-> XmlUtil.serialize(rootNode, out) }
            InputStream iss = new FileInputStream("/${System.getProperty('user.home')}/temp.xml")
            job.updateByXml(new StreamSource(iss));
            job.save();         
			println 'save done'
        }
    }
	
	return true
}
