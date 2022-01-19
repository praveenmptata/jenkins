@groovy.transform.Field
def sample = false

def call() {
	 cool()
	 println "hello : ${sample}"
}

def cool() {
    println "cool :${sample}"
	sample = true
	println "cool : ${sample}"
}