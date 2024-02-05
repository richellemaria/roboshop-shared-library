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
        NEXUS=credentials('NEXUS')
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
        stage('Perpare the artifacts'){
            when{ expression { env.TAG_NAME != null } }
            steps{
                sh "echo Prepare artifacts ${COMPONENT}"
                sh "npm install"
                sh "zip ${COMPONENT}.zip node_modules server.js"
            }
        }
        stage('upload Artifacts'){
            when{ expression { env.TAG_NAME != null } }
            steps{
                sh "echo Uploading artifacts to nexus"
                sh "curl -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload -file ${COMPOMENT}.${TAG_NAME}.zip http://localhost:8081/repository/${COMPOMENT}/${COMPOMENT}.${TAG_NAME}.zip"
                sh "echo Uploading artifacts to nexus is completed"
            }
        }
    }
    } 
} 