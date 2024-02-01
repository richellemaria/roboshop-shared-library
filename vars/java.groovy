def Lintcheck(){
   sh '''
        echo Performing lintcheck for  ${COMPONENT}
        mvn checkstyle:check || true
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
                sh "mvn clean compile"
            }
        }
    }
    } 
} 