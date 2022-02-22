def call() {
    hello1 = sh(script: 'echo hello', returnStdout: true).trim()
	print()
}

def print() {
    println hello1
}