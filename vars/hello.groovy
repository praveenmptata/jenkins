def call() {
    lock('ODSC_CU_UT_RES') {
	    def y = 1
		sleep 5
	}
	println y
}