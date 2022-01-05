
package com

@NonCPS
String runCmdAndGetOutput(String cmd) {
        printf("cmd recevied : %s", cmd)
        def proc = cmd.execute()
    	println proc.text
        return proc.text
}
