package com

import java.io.InputStream;
import java.io.FileInputStream
import java.io.File;
import javax.xml.transform.stream.StreamSource
import groovy.util.*
import groovy.xml.*

@NonCPS
def getPipelineJobs(String jobname, String folderName) {
    def Ins = hudson.model.Hudson.instance
    def Jobs = Ins.getAllItems(org.jenkinsci.plugins.workflow.job.WorkflowJob)
    return Jobs.findAll { it.getFullName().contains(folderName) && it.name == jobname}
}


@NonCPS
def reloadJobConfig(String script, String jobname, String folderName) {
    def allJobs = getPipelineJobs(jobname, folderName)
    if (allJobs.isEmpty()) {
        println "Error : No Job ${jobname} found inside ${folderName}"
        return false
    }

    for (job in allJobs) {
        if (job.name == jobname) {
            println "Editing job ${jobname} inside folder ${folderName}"
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
    }
}

