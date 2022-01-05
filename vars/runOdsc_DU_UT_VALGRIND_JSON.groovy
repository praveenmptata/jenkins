def call(String filter, String workspace = ${WORKSPACE} ) {

    echo r > cmd.txt
    echo generate-core-file >> cmd.txt
    echo bt >> cmd.txt
    echo q >> cmd.txt
	
	if(filter.contains('Sch')) {
	    def report = 'xml:SCH_REPORT.xml'
	}
	else if(filter.contains('Rlc')) {
        def report = 'xml:RLC_REPORT.xml'
	}
	else {
        def report = 'xml:APP_REPORT.xml'
	}

    sh """
    export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:.
    gdb -batch -x cmd.txt --args ./gnb_du --gtest_filter=${filter} --gtest_output=${report} > DU_UT_log.txt
    
    cat ${workspace}/5gran_jio_odsc/5gran/du/build/du_ut/du_bin/bin/DU_UT_log.txt.txt
    
    cp ${workspace}/repo/products_tools/tools/Jenkin/gtest_report_parser.py .
    python gtest_report_parser.py SCH_REPORT.xml | tee du_ut.log
    status_du_ut=$(cat du_ut.log | grep 'DU UT FAILED' | wc -l)"""

    if (status_du_ut > 0 ) {
    	echo 'du ut failed text found'
		this.binary_check()
    	sh 'exit 1'
    }
}


binary_check(){
if [ -f core* ]; then
echo "Core file detected"
ls -l core*
sshpass -p "zxcv@1234" ssh -o StrictHostKeyChecking=no root@172.26.0.132 "mkdir -p /root/10x/${BUILD_NUMBER}/"
sshpass -p "zxcv@1234" scp -r -o StrictHostKeyChecking=no core* root@172.26.0.132:/root/10x/${BUILD_NUMBER}/
sshpass -p "zxcv@1234" scp -r -o StrictHostKeyChecking=no gnb_du root@172.26.0.132:/root/10x/${BUILD_NUMBER}/
rm -rf core*
echo "core file copied to : 172.26.0.132"
echo "Path : /root/10x/${BUILD_NUMBER}/"
echo "Login details : root/zxcv@1234"
else
echo "All is well"
fi
}