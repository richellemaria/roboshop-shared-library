def Lintcheck(){
   sh '''
        sh "echo installing jslint"
        sh "npm i jslint"
        sh "node_modules/jslint/bin/jslint.js server.js || true"
    '''            
}                

def call(){
    pipeline{
    agent {
        label 'WS'
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
    }
    } 
} 