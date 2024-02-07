def call(){
    node{
        env.APP_TYPE="angularjs"
        common.LintCheck()
        env.ARGS="-Dsonar.source=."  
        common.Sonarcheck()
        common.testCases()
        if(env.TAG_NAME != null) {
            common.artifacts()
        }

    }
}

// def Lintcheck(){
//    sh '''
//         echo Performing lintcheck for  ${COMPONENT}
//         echo lint checks completed for ${COMPONENT}
//     '''            
// }                

// def call(COMPONENT){
//     pipeline{
//     agent {
//         label 'WS'
//     }
//     stages{
//         stage('Lint Check'){
//             steps{
//                 script{
//                   env.ARGS="-Dsonar.source=."  
//                   Lintcheck()
//                 }
//             }
//         }
//         stage('Sonar check'){
//             steps{
//                 script{
//                   env.ARGS="-Dsonar.source=."  
//                   common.Sonarcheck()
//                 }
//             }
//         }
//         stage('Testing'){
//             steps{
//                 sh "echo testing inprogress"
//             }
//         }
//     }
//     }
//     } 
// } 