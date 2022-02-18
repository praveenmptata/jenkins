def call() {
    def y = 2
    lock('ODSC_CU_UT_RES') {
	    y = 1
		sleep 5
	}
	println y
}