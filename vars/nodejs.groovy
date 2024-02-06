// def Lintcheck(){
//    sh '''
//         echo installing jslint ${COMPONENT}
//         npm i jslint
//         node_modules/jslint/bin/jslint.js server.js || true
//     '''            
// }  

def call()
{
    node {
        common.LintCheck()
    }
}

// def call(COMPONENT){
//     pipeline{
//     agent {
//         label 'WS'
//     }
//     enivronment{
//         SONARCRED=credentials('SONARCRED')
//         NEXUS=credentials('NEXUS')
//         SONARURL="172.31.6.159"
//         NEXUSURL=""
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
//                 sh "npm install"
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
//             parallel{
//                 stage('Unit Testing'){
//                     steps{
//                         sh "echo unit testing started"
//                         sh "echo unit testinbg completed" 
//                     }
//                 }
//                 stage('Intergration Testing'){
//                     steps{
//                         sh "echo Intergration testing started"
//                         sh "echo Intergration testinbg completed" 
//                     }
//                 }
//                 stage('Functional Testing'){
//                     steps{
//                         sh "echo Functional testing started"
//                         sh "echo Functional testinbg completed" 
//                     }
//                 }
//             }
//         }
//         stage('Validate the artifacts version '){
//             when{ 
//                 expression { env.TAG_NAME != null } 
//                 }
//             steps{
//                 env.UPLOAD_STATUS=sh(returnStdout: true, script: 'curl -L -s http://${NEXUSURL}:8081/service/rest/repository/browse/${COMPONENT} | grep ${COMPONENT}-${TAG_NAME}.zip || true')
//                 print UPLOAD_STATUS
//             }
//         }
//         stage('Perpare the artifacts'){
//             when{ 
//                 expression { env.TAG_NAME != null } 
//                 expression { env.UPLOAD_STATUS == "" }
//                 }
//             steps{
//                 sh '''
//                   echo Prepare artifacts ${COMPONENT}
//                   npm install
//                   zip ${COMPONENT}.${TAG_NAME}.zip node_modules server.js
//                 '''
//             }
//         }
//         stage('upload Artifacts'){
//             when{ 
//                 expression { env.TAG_NAME != null } 
//                 expression { env.UPLOAD_STATUS == "" }
//                }
//             steps{
//                 sh '''
//                   echo Uploading artifacts to nexus
//                   curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload -file ${COMPOMENT}.${TAG_NAME}.zip http://localhost:8081/repository/${COMPOMENT}/${COMPOMENT}.${TAG_NAME}.zip
//                   echo Uploading artifacts to nexus is completed
//                 '''
//             }
//         }
//     }
//     } 
// } 