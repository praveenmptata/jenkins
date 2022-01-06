def call(Map config = [:]) {
    sh "echo Hello"
	def status_du_ut = sh(script : "cat du_ut.log | grep 'DU UT FAILED' | wc -l", returnStdout: true).trim()
	def aval_reports = sh (script : "find . -name *REPORT.xml", returnStdout: true).trim()
    printf("du_ut :%s , aval : %s\n", status_du_ut, aval_reports)

    if (status_du_ut.toInteger() > 0 ) {
            echo 'du ut failed text found'
    }

	sh """
        if [ -f core* ]; then
            echo 'Core file detected'
            ls -l core*
            sshpass -p 'zxcv@1234' ssh -o StrictHostKeyChecking=no root@172.26.0.132 'mkdir -p /root/10x/${BUILD_NUMBER}/'
            sshpass -p 'zxcv@1234' scp -r -o StrictHostKeyChecking=no core* root@172.26.0.132:/root/10x/${BUILD_NUMBER}/
            sshpass -p 'zxcv@1234' scp -r -o StrictHostKeyChecking=no gnb_du root@172.26.0.132:/root/10x/${BUILD_NUMBER}/
            rm -rf core*
            echo 'core file copied to : 172.26.0.132'
            echo 'Path : /root/10x/${BUILD_NUMBER}/'
            echo 'Login details : root/zxcv@1234'
        else
            echo 'All is well'
        fi """
}