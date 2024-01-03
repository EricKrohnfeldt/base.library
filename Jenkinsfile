def releaseCandidate = false;
pipeline {
	agent none
	stages {
		stage( 'Deploy Snapshot' ) {
			when { beforeAgent true; not { branch pattern: 'master(-\\d+)?', comparator: 'REGEXP' } }
			agent { docker { image env.DOCKER_IMAGE; args env.DOCKER_ARGS; registryUrl env.DOCKER_URL; registryCredentialsId env.DOCKER_CREDS } }
			steps {
				milestone 1
				sh 'mvn clean deploy javadoc:javadoc'
			}
		}
		stage( 'Prepare merge' ) {
			when { beforeAgent true; not { branch pattern: 'master(-\\d+)?', comparator: 'REGEXP' } }
			agent { docker { image env.DOCKER_IMAGE; args env.DOCKER_ARGS; registryUrl env.DOCKER_URL; registryCredentialsId env.DOCKER_CREDS } }
			steps {
				sh "git branch -d jenkins_${BUILD_NUMBER} || true"
				sh "git checkout -b jenkins_${BUILD_NUMBER}"
				sh 'git checkout master'
				sh 'git reset --hard origin/master'
				script {
					releaseCandidate = 0 == sh(
						script: "git merge-base --is-ancestor master jenkins_${BUILD_NUMBER} && [ \"\$(git rev-parse master)\" != \"\$(git rev-parse jenkins_${BUILD_NUMBER})\" ]",
						returnStatus: true
					)
				}
				sh "echo Release Candidate: $releaseCandidate"
				sh "[ true != $releaseCandidate ] && git log --all --decorate --oneline --graph | head -n50 || true"
			}
		}
		stage( 'Confirm merge' ) {
			when { beforeAgent true; not { branch pattern: 'master(-\\d+)?', comparator: 'REGEXP' }; expression { releaseCandidate } }
			steps {
				script {
					try {
						timeout( time: 15, unit: 'MINUTES' ) {
							input 'Merge to Master?'
						}
						milestone 2
					}
					catch ( Exception e ) {
						releaseCandidate = false
						currentBuild.result = 'SUCCESS'
						println "Cancelled build: ${currentBuild.result}"
						println "Cancelled build: ${currentBuild.currentResult}"
					}
				}
			}
		}
		stage( 'Deploy Release' ) {
			when { beforeAgent true; not { branch pattern: 'master(-\\d+)?', comparator: 'REGEXP' }; expression { releaseCandidate } }
			agent { docker { image env.DOCKER_IMAGE; args env.DOCKER_ARGS; registryUrl env.DOCKER_URL; registryCredentialsId env.DOCKER_CREDS } }
			steps {
				milestone 3
				sh "git branch -d jenkins_${BUILD_NUMBER} || true"
				sh "git checkout -b jenkins_${BUILD_NUMBER}"
				sshagent( [ 'KirbyGitKey' ] ) {
					sh 'git fetch --all'
					sh 'git checkout master'
					sh 'git reset --hard origin/master'
					sh "git merge --ff-only jenkins_${BUILD_NUMBER}"
					sh '#mvn release:prepare release:perform --batch-mode'
				}
			}
		}
		stage( 'Deploy JavaDoc' ) {
			when { beforeAgent true; not { branch pattern: 'master(-\\d+)?', comparator: 'REGEXP' }; expression { releaseCandidate } }
			agent { docker { image env.DOCKER_IMAGE; args env.DOCKER_ARGS; registryUrl env.DOCKER_URL; registryCredentialsId env.DOCKER_CREDS } }
			steps {
				milestone 4
				script {
					artifactVersion = sh(
						script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout",
						returnStatus: true
					)
				}
				sh "echo Artifact version: $artifactVersion"
				sshagent( [ 'KirbyGitKey' ] ) {
					sh 'pwd && git clone git@git.herb.herbmarshall.com:repository/util/javadoc.info'
					sh 'pwd && cd javadoc.info'
					sh 'pwd && git checkout work'
					sh "pwd && cp -r ../target/target/site/apidocs site/${JOB_NAME}/${artifactVersion}"
					sh 'pwd && git add site/${JOB_NAME}/${artifactVersion}'
					sh 'pwd && git commit -m "Add "${JOB_NAME}/${artifactVersion}" docs'
					sh 'pwd && git push'
				}
			}
		}
	}
	environment {
		DOCKER_IMAGE = 'docker.herb.herbmarshall.com/maven.herb'
		DOCKER_ARGS = '-v maven-data:/home/jenkins/.m2'
		DOCKER_URL = 'https://docker.herb.herbmarshall.com/'
		DOCKER_CREDS = 'Nexus'
	}
}
