
package com


String runCmdAndGetOutput(String cmd) {
    printf("cmd recevied : %s", cmd)
    def proc = cmd.execute()
    return proc.text
}
