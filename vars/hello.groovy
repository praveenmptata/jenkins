@groovy.transform.Field
def hello1 = sh(script: 'echo hello', returnStdout: true).trim()

def call() {
	println hello1
}