
package com

/*** BEGIN META {
  "name" : "reload job config",
  "comment" : "Reload the job config of a specific job.",
  "parameters" : [],
  "core": "1.300",
  "authors" : [
    { name : "Thomas Froehlich - mail@thomas-froehlich.net" }
  ]
} END META**/


import java.io.InputStream;
import java.io.FileInputStream
import java.io.File;
import javax.xml.transform.stream.StreamSource


String reloadJobConfig(String script, String toBeCopiedJobName, String folderName) {
    def hudson = hudson.model.Hudson.instance;
    def allJobs = hudson.model.Hudson.instance.getAllItems(Job.class).findAll { it.getFullName().contains(folderName) && it.name == toBeCopiedJobName} ;
    if (allJobs.isEmpty()) {
        println 'Error : No Job ${toBeCopiedJobName} found inside ${folderName}'
		return false
    }

	for (job in allJobs) {
	    if (jon.name == toBeCopiedJobName) {
            def configXMLFile = job.getConfigFile();
            def file = configXMLFile.getFile();
            
            InputStream is = new FileInputStream(file);
            content = is.getText("UTF-8") 
	        
            job.updateByXml(new StreamSource(is));
            job.save();         
        }
    }
	
	return true
}
