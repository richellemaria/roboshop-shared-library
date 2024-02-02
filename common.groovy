def Sonarcheck(){
   sh '''
        echo Sonar check Inprogress
        sonar-scanner -Dsonar.source=. -Dsonar.login=971b90e060ec03306b76af963845170e8e5b5687 -Dsonar.host.url=http://172.31.6.159:9000 -Dsonar.projectKey=${COMPONENT}
        curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > sonar.quality.gate.sh
        sonar.quality.gate.sh ${SONARCRED_USR} ${SONARCRED_PSW} ${SONARURL} ${COMPONENT}
        echo Sonar check Completed
    '''            
} 