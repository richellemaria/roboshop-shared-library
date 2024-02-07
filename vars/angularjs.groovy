def call(){
    node{
        common.LintCheck()
        env.ARGS="-Dsonar.source=."  
        common.Sonarcheck()

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