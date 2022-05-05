# File: scm_build_ci.sh
# Description: this script will contain the build logic for a CI job
# Author: JanJaap Lahpor, x72309

source ${WORKSPACE}/build/scm_build_environment.sh
export MAVEN_PROFILES="default,rpm"

#Jersey config test container port

if [ "x${1}" != "x" ]; then
JERSEY_CONFIG_TEST_CONTAINER_PORT="-Djersey.config.test.container.port=${1}"
fi


# Lets get building!
perl ${WORKSPACE}/build/scm_get_pom_version_number.pl ${WORKSPACE}/pom.xml ${WORKSPACE}/base_version_number.txt
BASE_VERSION_NUMBER=`cat ${WORKSPACE}/base_version_number.txt`

# Lets print the environment for troubleshooting
set

# Execute release plug-in
echo "${MAVEN_HOME}/bin/mvn -B -e -Dsurefire.useFile=false -s ${MAVEN_SETTINGS} -Dmaven.repo.local=${MAVEN_REPO} -DpreparationGoals="clean" release:prepare -DreleaseVersion=${BASE_VERSION_NUMBER}.${BUILD_NUMBER} -DdevelopmentVersion=${BASE_VERSION_NUMBER}-SNAPSHOT ${JERSEY_CONFIG_TEST_CONTAINER_PORT} -P ${MAVEN_PROFILES} -Dusername=git"


${MAVEN_HOME}/bin/mvn -B -e -Dsurefire.useFile=false -s ${MAVEN_SETTINGS} -Dmaven.repo.local=${MAVEN_REPO} -DpreparationGoals="clean" release:prepare -DreleaseVersion=${BASE_VERSION_NUMBER}.${BUILD_NUMBER} -DdevelopmentVersion=${BASE_VERSION_NUMBER}-SNAPSHOT ${JERSEY_CONFIG_TEST_CONTAINER_PORT} -P ${MAVEN_PROFILES} -Dusername=git

echo "${MAVEN_HOME}/bin/mvn -B -e -Dsurefire.useFile=false -s ${MAVEN_SETTINGS} -Dmaven.repo.local=${MAVEN_REPO} ${JERSEY_CONFIG_TEST_CONTAINER_PORT} -DpreparationGoals="install" release:perform -P ${MAVEN_PROFILES} -Dusername=git"

${MAVEN_HOME}/bin/mvn -B -e -Dsurefire.useFile=false -s ${MAVEN_SETTINGS} -Dmaven.repo.local=${MAVEN_REPO} ${JERSEY_CONFIG_TEST_CONTAINER_PORT} -DpreparationGoals="install" release:perform -P ${MAVEN_PROFILES} -Dusername=git

if [[ $? -ne 0 ]] ; then
    exit 1
fi

echo VERSION_NUMBER=${BASE_VERSION_NUMBER}.${BUILD_NUMBER}>downstream.properties

