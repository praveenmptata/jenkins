package com

import java.io.InputStream;
import java.io.FileInputStream
import java.io.File;
import javax.xml.transform.stream.StreamSource
import groovy.util.*
import groovy.xml.*

@NonCPS
def getPipelineJobs(String jobname) {
    def Ins = hudson.model.Hudson.instance
    def Jobs = Ins.getAllItems(org.jenkinsci.plugins.workflow.job.WorkflowJob)
    return Jobs.findAll { it.name == jobname}
}


@NonCPS
def reloadJobConfig(String script, String jobname) {

    def allJobs = getPipelineJobs(jobname)
    if (allJobs.isEmpty()) {
        println "Error : No Job ${jobname} found inside ${folderName}"
        return false
    }
    allJobs.each {println it.getFullName()}

	def job = hudson.model.Hudson.instance.getItemByFullName(jobname)
	assert job != null && job instanceof org.jenkinsci.plugins.workflow.job.WorkflowJob : 'Input is not a pipeline job'

    if (job.getFullName() == jobname) {
        println "Editing job ${jobname}"
        def configXMLFile = job.getConfigFile()
        def file = configXMLFile.getFile()

        def rootNode = new XmlSlurper().parse(file)
        def myNode = rootNode.depthFirst().find { it.name() == 'script'}
        myNode.replaceBody(script)
        file.withWriter {out-> XmlUtil.serialize(rootNode, out) }

        InputStream is = new FileInputStream(file)
        job.updateByXml(new StreamSource(is))
        job.save()
        return true
    }
    return false
}

