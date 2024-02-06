def call(){
 node{
   common.Lintcheck()
 }
}

// def Lintcheck(){
//    sh '''
//        echo Performing lintcheck for  ${COMPONENT}
//        #pip3 install pylint
//        echo lint checks completed for ${COMPONENT}
//     '''            
// }                

// def call(COMPONENT){
//     pipeline{
//     agent {
//         label 'WS'
//     }
//     enivronment{
//         SONARCRED=credentials('SONARCRED')
//         SONARURL="172.31.6.159"
//     }
//     stages{
//         stage('Lint Check'){
//             steps{
//                 script{
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
// } 