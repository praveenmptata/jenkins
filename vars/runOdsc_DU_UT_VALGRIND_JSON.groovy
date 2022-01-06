def call(String filter, Map Inputs = [:] ) {
    default_inputs = [workspace: ${WORKSPACE}, validate_ut: true, valgrind: false]
    Inputs = default_inputs + Inputs

    println "UT filter : ${filter} \n Inputs : ${Inputs.toMapString()}"

    if (! fileExists("${workpacePath}/5gran_jio_odsc/5gran/du/build/du_ut/du_bin/bin/gnb_du"))
    {
        echo "DU compilation failed"
        sh 'exit 1'
    }
	
    if(filter.contains('Sch')) {
        def ut_report = 'xml:SCH_REPORT.xml'
        def val_report = "sch_valgrind_report"
    }
    else if(filter.contains('Rlc')) {
        def ut_report = 'xml:RLC_REPORT.xml'
        def val_report = "rlc_valgrind_report"
    }
    else {
        def ut_report = 'xml:APP_REPORT.xml'
        def val_report = "app_valgrind_report"
    }

    writeFile file: "cmd.txt", text: ["r", "bt", "generate-core-file", "q"].join("\n")
    sh """
    sed  -i 's|OAM_JSON_CFG_FILE_NAME =.*|OAM_JSON_CFG_FILE_NAME = ..\/config\/oam_3gpp_cell_cfg_mu3_6cell.json|g' ${Inputs.workspace}/5gran_jio_odsc/5gran/du/build/du_ut/du_bin/config/oam_du_json_cfg.txt
    export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:."""

    if (Inputs.valgrind) {
        sh """
        timeout --preserve-status 45m valgrind --tool=memcheck -v --leak-check=full --show-reachable=yes --time-stamp=yes --log-file=${val_report} ./gnb_du --gtest_filter=${filter} --gtest_output=${ut_report}
        """
    }
    else {
        sh """
        gdb -batch -x cmd.txt --args ./gnb_du --gtest_filter=${filter} --gtest_output=${ut_report} > DU_UT_log.txt
        cat ${Inputs.workspace}/5gran_jio_odsc/5gran/du/build/du_ut/du_bin/bin/DU_UT_log.txt.txt"""
    }

    if (Inputs.validate_ut) {
        sh """cp ${Inputs.workspace}/repo/products_tools/tools/Jenkin/gtest_report_parser.py ."""

        def aval_reports = sh (script : "find . -name *REPORT.xml", returnStdout: true).trim()
        aval_reports = aval_reports.split().join(' ')

        sh """python gtest_report_parser.py ${aval_reports} | tee du_ut.log"""

        def status_du_ut = sh(script : "cat du_ut.log | grep 'DU UT FAILED' | wc -l", returnStdout: true).trim()

        if (status_du_ut.toInteger() > 0 ) {
            echo 'du ut failed text found'
	        this.checkAndCopyCore()
            sh 'exit 1'
        }

        if (Inputs.valgrind) {
            sh """
            cp ${Inputs.workspace}/5gran_jio_odsc/5gran/tools/Jenkin/Valgrind_diff_genartor.py .
            rm -rf New_Valgrind Diff Diff_SUMMARY.log
            mkdir New_Valgrind; cp -r ${val_report} New_Valgrind
            python3.6 Valgrind_diff_genartor.py New_Valgrind Base_Valgrind
            cp Diff_SUMMARY.log ${WORKSPACE}"""
        }
    }
}


checkAndCopyCore(){
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