def Lintcheck(){
   sh '''
        echo Performing lintcheck for  ${COMPONENT}
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
        stage('Code Quality analysis'){
            steps{
                //sh "mvn clean compile"
            }
        }
    }
    } 
} 