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
        stage('Sonar check') {
                sh "echo Sonar check Inprogress"
                sh "sonar-scanner -Dsonar.login=admin -Dsonar.password=password -Dsonar.host.url=http://172.31.6.159:9000 -Dsonar.projectKey=${COMPONENT} ${ARGS}"
                sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > sonar.quality.gate.sh"
                sh "sonar.quality.gate.sh ${SONARCRED_USR} ${SONARCRED_PSW} ${SONARURL} ${COMPONENT} | true"
                sh "echo Sonar check Completed"   
        }         
} 

def testCases(){
        stage('Test Cases'){
                def stages = [:]

                stages["Unit Testing"] = {
                        echo "unit testinng for ${COMPONENT}"
                }
                stages["Intergration Testing"] = {
                        echo "Intergration testinng for ${COMPONENT}"
                }
                stages["Functional Testing"] = {
                        echo "Functional testinng for ${COMPONENT}"
                }
                parallel(stages)   
        }
}

def artifacts() {
        
        stage('Validate Artifact Version') {
            env.UPLOAD_STATUS=sh(returnStdout: true, script: "curl -L -s http://172.31.92.189:8081/service/rest/repository/browse/${COMPONENT} | grep ${COMPONENT}-${TAG_NAME}.zip || true" )
            print UPLOAD_STATUS
        }                    
                
        if(env.UPLOAD_STATUS == "") {
                stage('Prepare Artifacts') {
                        if(env.APP_TYPE == "nodejs"){
                                sh '''
                                        echo Preparing Artifacts for ${COMPONENT}
                                        npm install
                                        zip -r ${COMPONENT}-${TAG_NAME}.zip node_modules server.js                        
                                '''
                        }
                        else if(env.APP_TYPE == "python"){
                                sh '''
                                        echo Preparing Artifacts for ${COMPONENT}
                                        zip -r ${COMPONENT}-${TAG_NAME}.zip *.py *.ini  requirements.txt                     
                                '''
                        }
                        else if(env.APP_TYPE == "java"){
                                sh '''
                                        echo Preparing Artifacts for ${COMPONENT}
                                        mvn clean package
                                        mv target/${COMPONENT}-1.0.jar ${COMPONENT}.jar
                                        zip -r ${COMPONENT}-${TAG_NAME}.zip  ${COMPONENT}.jar          
                                '''
                        }
                        else {
                                sh '''
                                        echo Preparing Artifacts for ${COMPONENT}
                                        cd static
                                        zip -r ../${COMPONENT}-${TAG_NAME}.zip *                  
                                '''
                        }
                }
                
                stage('Upload Artifacts') {
                        withCredentials([usernamePassword(credentialsId: 'NEXUS', passwordVariable: 'NEXUS_PSW', usernameVariable: 'NEXUS_USR')]) {
                                sh "echo Uploading ${COMPONENT} Artifacts To Nexus"
                                sh "curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip  http://172.31.92.189:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip || true"
                                sh "echo Uploading ${COMPONENT} Artifacts To Nexus is Completed"                   
                                
                        }                
                }
        }
}