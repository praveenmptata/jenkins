@groovy.transform.Field
def sample = false

def call() {
	 cool()
}

def cool() {
    println "${sample}"
}