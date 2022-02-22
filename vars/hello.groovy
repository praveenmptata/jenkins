def call() {
    hello1 = sh(script: 'echo hello', returnStdout: true).trim()
	print()
	println hello1
}

def print() {
    println hello1
}