
def call(String workpacePath ) {

    sh "cd ${workpacePath}"
	sh '''
	source env_setup
	
	source /root/sdk/environment-setup-aarch64-poky-linux
	cd ${workpacePath}/5gran_jio_odsc/ngp/thirdparty/
	cp -rf ${workpacePath}/5g_jobs_thirdparty/dpdk_YOCTO_JIO_ODSC.tar.gz .
	tar -zxvf dpdk_YOCTO_JIO_ODSC.tar.gz

	cd ${workpacePath}/5gran_jio_odsc/ngp/build
	make TARGET=arm -j 20

	cd ${workpacePath}/5gran_jio_odsc/5gran/cu/build/
	./build_cu_arm.sh '''
	

    if (fileExists("${workpacePath}/5gran_jio_odsc/5gran/cu/build/cu_bin/bin/gnb_cu")) 
    {
        echo "***** gnb_cu binary is generated*****"
    }
	else
	{
		echo "gnb_cu is not generted"
	}
}
