import com.Utils

def call() {
	def num = new Utils().getInstanceNumber() 
	println "executor: ${num}" 
}
