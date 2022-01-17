def call(String in, String config = 'hello', Map cfg = [:]) {
    println config
    println in
	println "Inputs : ${cfg.toMapString()}"
	
}