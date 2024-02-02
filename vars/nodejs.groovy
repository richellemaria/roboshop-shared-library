def Lintcheck(){
   sh '''
        echo installing jslint ${COMPONENT}
        npm i jslint
        node_modules/jslint/bin/jslint.js server.js || true
    '''            
}  

def Sonarcheck(){
   sh '''
        echo Sonar check Inprogress
        sonar-scanner -Dsonar.source=. -Dsonar.login=971b90e060ec03306b76af963845170e8e5b5687 -Dsonar.host.url=http://172.31.6.159:9000 -Dsonar.projectKey=${COMPONENT}
        curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > sonar.quality.gate.sh
        sonar.quality.gate.sh ${SONARCRED_USR} ${SONARCRED_PSW} ${SONARURL} ${COMPONENT}
        echo Sonar check Completed
    '''            
} 

def call(COMPONENT){
    pipeline{
    agent {
        label 'WS'
    }
    enivronment{
        SONARCRED=credentials('SONARCRED')
        SONARURL="172.31.6.159"
    }
    stages{
        stage('Lint Check'){
            steps{
                script{
                  Lintcheck()
                }
            }
        }
        stage('Code Complie'){
            steps{
                sh "npm install"
            }
        }
        stage('Sonar check'){
            steps{
                script{
                  Sonarcheck()
                }
            }
        }
        stage('Testing'){
            steps{
                sh "echo testing inprogress"
            }
        }
    }
    } 
} 