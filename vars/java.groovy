def call(){
 node{
   env.APP_TYPE="java"
    common.Lintcheck()
    env.ARGS="-Dsonar.java.binaries=target/"
    common.Sonarcheck()
    common.testCases()
 }
}


// def Lintcheck(){
//    sh '''
//         echo Performing lintcheck for  ${COMPONENT}
//         mvn checkstyle:check || true
//         echo lint checks completed for ${COMPONENT}
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
//         stage('Code Complie'){
//             steps{
//                 sh "mvn clean compile"
//             }
//         }
//         stage('Sonar check'){
//             steps{
//                 script{
//                   env.ARGS="-Dsonar.java.binaries=target/"
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