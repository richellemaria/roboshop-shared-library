def Lintcheck(){
        stage('Lint Check'){
                if(env.APP_TYPE == "nodejs"){
                        sh '''
                                echo installing jslint ${COMPONENT}
                                npm i jslint
                                node_modules/jslint/bin/jslint.js server.js || true
                        ''' 
                } 
                else if(env.APP_TYPE == "python") {
                        sh '''
                                pip3 install pylint
                                pip *.py
                                echo lint checks started for ${COMPONENT} using pylint
                                echo lint checks completed for ${COMPONENT}
                        '''
                }
                else if(env.APP_TYPE == "java") {
                        sh '''
                                mvn checkstyle:check
                                echo lint checks started for ${COMPONENT} 
                                echo lint checks completed for ${COMPONENT}
                        '''
                }
                else{
                 sh '''       
                   echo lint checks started for ${COMPONENT} using angularjs 
                   echo lint checks completed for ${COMPONENT} 
                  '''     
                }
        }          
}

def Sonarcheck(){
        sh "echo Sonar check Inprogress"
        sh "sonar-scanner -Dsonar.login=admin -Dsonar.password=password -Dsonar.host.url=http://172.31.6.159:9000 -Dsonar.projectKey=${COMPONENT} ${ARGS}"
        sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > sonar.quality.gate.sh"
        sh "sonar.quality.gate.sh ${SONARCRED_USR} ${SONARCRED_PSW} ${SONARURL} ${COMPONENT} | true"
        sh "echo Sonar check Completed"            
} 