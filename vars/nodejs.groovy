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
                  env.ARGS="-Dsonar.source=."  
                  common.Sonarcheck()
                }
            }
        }
        stage('Testing'){
            parallel{
                stage('Unit Testing'){
                    steps{
                        sh "echo unit testing started"
                        sh "echo unit testinbg completed" 
                    }
                }
                stage('Intergration Testing'){
                    steps{
                        sh "echo Intergration testing started"
                        sh "echo Intergration testinbg completed" 
                    }
                }
                stage('Functional Testing'){
                    steps{
                        sh "echo Functional testing started"
                        sh "echo Functional testinbg completed" 
                    }
                }
            }
        }
    }
    } 
} 