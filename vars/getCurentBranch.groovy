def call(String component) {
    sh 'pwd'
    Map repoMap = [cu : "products_cu",
                    du : "5gran_jio_odsc",
                    platform : "platform_services"]

    if(! repoMap.containsKey(component)) {
        println "Invalid input for Function: ${component}"
        sh 'exit 1'
    }

    def branch = sh(script: "repo info ${repoMap[component.toLowerCase()]} | grep 'Manifest revision' | cut -d ':' -f 2",
                                                                        returnStdout: true).trim()
    println "${component} : ${branch}"
    return branch
}