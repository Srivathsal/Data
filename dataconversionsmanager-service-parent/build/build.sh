#!/bin/sh

set -e

VERSION_NUMBER="$(xmllint --xpath "//*[local-name()='project']/*[local-name()='version']/text()" pom.xml | sed -e 's/-SNAPSHOT//g')"

if [ "$1" == "SNAPSHOT" ]; then
  VERSION_NUMBER="$VERSION_NUMBER-SNAPSHOT"
  echo "Detected a SNAPSHOT build - going to build $VERSION_NUMBER"
  #skip test for moving fast
  SKIP_TEST="true"
else
  VERSION_NUMBER="$VERSION_NUMBER.$1"
  SKIP_TEST="false"

  echo "Detected a RELEASE build - going to build $VERSION_NUMBER"

  mvn versions:set -DnewVersion=$VERSION_NUMBER -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn

  if [[ $? -ne 0 ]] ; then
      exit 1
  fi
fi

mvn clean install -DskipTests=$SKIP_TEST -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn

if [[ $? -ne 0 ]] ; then
    exit 1
fi

echo $VERSION_NUMBER>/app/version_number.txt
