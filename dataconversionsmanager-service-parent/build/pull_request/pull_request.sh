#!/bin/sh

# function to print formatted header
function print_header {
  echo $'\n'"###################################################################"
  echo "$1"
  echo "###################################################################"
}

mkdir /temp

chmod 755 ${WORKSPACE}/build/pull_request/*.sh

echo "-------------------------------------------------------------------"

echo "Workspace directory ${WORKSPACE}"

echo "MAVEN_HOME ${MAVEN_HOME}"
echo "MAVEN_REPO ${MAVEN_REPO}"
echo "MAVEN_SETTINGS ${MAVEN_SETTINGS}"

echo "CUCUMBER_TAGS ${CUCUMBER_TAGS}"
echo "REGRESSION_PROFILE ${REGRESSION_PROFILE}"

echo "Service Version ${RELEASE_VERSION_NUMBER}"
echo "-------------------------------------------------------------------"

source ${WORKSPACE}/build/pull_request/prbuild_aws.cfg

cd ${WORKSPACE}/webapp

print_header "1. Running JUnits with Coverage"

echo ${MVN_CMD} -f ${WORKSPACE}/webapp/pom.xml clean install -P default
${MAVEN_HOME}/bin/mvn -f ${WORKSPACE}/webapp/pom.xml clean install -P default

## Clover creates this directory
rm ${WORKSPACE}/webapp/target/reminders-service.war &>/dev/null

set e-
cd ${WORKSPACE}/test-suite

print_header "2. Triggering integration tests"

# Do not use -Dsurefire.useFile=false as we depend on txt files to pass or fail the step
echo ${MAVEN_HOME}/bin/mvn -B -e -Dsurefire.useFile=false -s ${MAVEN_SETTINGS} -Dmaven.repo.local=${MAVEN_REPO}  -DpreparationGoals="clean" -P ${REGRESSION_PROFILE} -DpropertiesFile=prbuild.properties -Dmaven.test.failure.ignore=false clean install surefire-report:report-only
${MAVEN_HOME}/bin/mvn -B -e -Dsurefire.useFile=false -s ${MAVEN_SETTINGS} -Dmaven.repo.local=${MAVEN_REPO} -DpreparationGoals="clean" -P ${REGRESSION_PROFILE} -DpropertiesFile=prbuild.properties -Dmaven.test.failure.ignore=false clean install surefire-report:report-only
