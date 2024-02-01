def Lintcheck(){
   sh '''
       echo Performing lintcheck for  ${COMPONENT}
       #pip3 install pylint
       echo lint checks completed for ${COMPONENT}
    '''            
}                

def call(COMPONENT){
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