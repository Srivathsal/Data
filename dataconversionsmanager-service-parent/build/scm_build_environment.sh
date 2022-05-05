export JAVA_VERSION="1.8.0_152"
export JAVA_HOME="/app/javavms/jdk${JAVA_VERSION}"

export MAVEN_VERSION="3.3.1"
export MAVEN_HOME="/app/maven/${MAVEN_VERSION}"
export MAVEN_OPTS=" -Xmx1024m -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn"
export MAVEN_SETTINGS="/app/maven/settings.xml"
export MAVEN_REPO="/builds/m2_repo"
export PATH="/usr/local/bin:/bin:/usr/bin"
