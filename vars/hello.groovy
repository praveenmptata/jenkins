@groovy.transform.Field
def hello = sh script: 'echo hello', returnStdout: true).trim()

def call() {
	println hello
}