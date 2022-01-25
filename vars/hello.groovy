def call() {
    retry(2) {
	     if(true) {
	         println "hello"
		 }
	 }
}