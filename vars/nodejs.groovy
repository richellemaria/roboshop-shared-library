def Lintcheck(){
   sh '''
        echo installing jslint ${COMPONENT}
        npm i jslint
        node_modules/jslint/bin/jslint.js server.js || true
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
                  common.Sonarcheck()
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