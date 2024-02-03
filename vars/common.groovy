def Sonarcheck(){
        sh "echo Sonar check Inprogress"
        sh "sonar-scanner -Dsonar.login=admin -Dsonar.password=password -Dsonar.host.url=http://172.31.6.159:9000 -Dsonar.projectKey=${COMPONENT} ${ARGS}"
        sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > sonar.quality.gate.sh"
        sh "sonar.quality.gate.sh ${SONARCRED_USR} ${SONARCRED_PSW} ${SONARURL} ${COMPONENT}"
        sh "echo Sonar check Completed"            
} 