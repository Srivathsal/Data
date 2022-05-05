#!/usr/bin/env bash
set -e
set -x
# if uc in CFG_DC (short for usw2 etc.
if [[ ${CFG_DC} =~ .*us*. ]]; then
	# Everything AWS goes here
	# setup AWS cli

	#find containerId
	ecs_taskARN=$( cat $ECS_CONTAINER_METADATA_FILE | grep "TaskARN")
  IFS='/' read -r -a array <<< "$ecs_taskARN"
  ecsTaskId=${array[1]:0:36}

	/tmp/aws.sh

	# download appsecret from bucket
	/tmp/aws s3 cp s3://${S3_BUCKET}/appsecret /usr/local/tomcat/webapps-config/appsecret --sse
	# download idps pem
	/tmp/aws s3 cp s3://${S3_BUCKET}/idps_pem /usr/local/tomcat/webapps-config/${IDPS_PEM} --sse
	# remove secrets from system
	\rm -rf /tmp/aws*
	set +x
	# Download configs
	if [[ "$CFG_ENV" == "prd" ]]; then
		/usr/local/tomcat/bin/intuit-cfg --cfg-v2 --cfg-url=https://config.api.intuit.com --log-file=/usr/local/tomcat/logs/intuitcfg.log --app-secret=$(cat /usr/local/tomcat/webapps-config/appsecret)
	else
		/usr/local/tomcat/bin/intuit-cfg --cfg-v2 --cfg-url=https://config-e2e.api.intuit.com --log-file=/usr/local/tomcat/logs/intuitcfg.log --app-secret=$(cat /usr/local/tomcat/webapps-config/appsecret)
	fi
else
	# Everything not AWS goes here
	if [[ "$CFG_ENV" == "loc" ]]; then
		echo "** Using config files bundled in the war file"
	else
        curl http://apdpdnexus.corp.intuit.net/nexus/content/repositories/platform/com/intuit/ptg/intuit-cfg/${DC_VERSION}/intuit-cfg-${DC_VERSION}.nix -o /tmp/intuit-cfg
        chmod 755 /tmp/intuit-cfg
        chmod 755 /usr/local/tomcat/webapps-config

        source /tmp/proxy.sh

        if [[ "$CFG_ENV" == "prd" ]]; then
            NO_PROXY="" no_proxy="" HTTP_PROXY="${PROXY}:80" /tmp/intuit-cfg --cfg-v2 --cfg-url=https://config.api.intuit.com --log-file=/usr/local/tomcat/logs/intuitcfg.log
        else
            NO_PROXY="" no_proxy="" HTTP_PROXY="${PROXY}:80" /tmp/intuit-cfg --cfg-v2 --cfg-url=https://config-e2e.api.intuit.com --log-file=/usr/local/tomcat/logs/intuitcfg.log
        fi
    fi
fi

# import security testing tool (contrast assess) for qal environments
if [[ ${CFG_DC} =~ .*us*. ]]; then
	source $(curl -s https://artifact.intuit.com/artifactory/maven.accounting.tax-accounting.utils-releases/com/intuit/security/contrast_assess/1.0.0.1/contrast_assess-1.0.0.1.sh -o /tmp/load_contrast_assess.sh; echo /tmp/load_contrast_assess.sh)
else
	source $(curl -x $PROXY:80 -s https://artifact.intuit.com/artifactory/maven.accounting.tax-accounting.utils-releases/com/intuit/security/contrast_assess/1.0.0.1/contrast_assess-1.0.0.1.sh -o /tmp/load_contrast_assess.sh; echo /tmp/load_contrast_assess.sh)
fi	
rm -f /tmp/load_contrast_assess.sh


# common for all

if [[ ! -d "/usr/local/tomcat/logs" ]]; then
	mkdir /usr/local/tomcat/logs
fi


if [[ "$CFG_ENV" == "loc" ]]; then
	export JAVA_OPTS="$JAVA_OPTS -Xms2g -Xmx2g -XX:ParallelGCThreads=${C_LIMIT_CPU} -XX:MaxMetaspaceSize=256m -XX:+UseG1GC -XX:MaxGCPauseMillis=500 -XX:+DisableExplicitGC -XX:-UsePerfData -Djava.awt.headless=true -server"
elif [[ "$CFG_ENV" == "prbuild" ]]; then
	export JAVA_OPTS="$JAVA_OPTS -Xms2g -Xmx2g -XX:ParallelGCThreads=${C_LIMIT_CPU} -XX:MaxMetaspaceSize=256m -XX:+UseG1GC -XX:MaxGCPauseMillis=500 -XX:+DisableExplicitGC -XX:-UsePerfData -Djava.awt.headless=true -server"
else
	chmod 755 /tmp/apm.sh /tmp/apm_config.sh
	source /tmp/apm.sh
	export JAVA_OPTS="$JAVA_OPTS -Xms2g -Xmx2g -XX:ParallelGCThreads=${C_LIMIT_CPU} -XX:MaxMetaspaceSize=256m -XX:+UseG1GC -XX:MaxGCPauseMillis=500 -XX:+PrintFlagsFinal -Xloggc:/usr/local/tomcat/logs/GC.log -verbose:sizes -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintTenuringDistribution -XX:+PrintGCCause -XX:+PrintAdaptiveSizePolicy -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=3 -XX:GCLogFileSize=200M -XX:+DisableExplicitGC -Djava.awt.headless=true -server"
  export ECS_TASK_ID=${ecsTaskId}
fi

ENV_LOG_FILE=/usr/local/tomcat/logs/intuitcfg.log
echo "DC_VERSION $DC_VERSION" >>${ENV_LOG_FILE}
echo "CFG_ENV $CFG_ENV" >>${ENV_LOG_FILE}
echo "CFG_DC $CFG_DC" >>${ENV_LOG_FILE}
echo "HOSTNAME $HOSTNAME" >>${ENV_LOG_FILE}
echo "ECS_TASK_ID $ecsTaskId" >>${ENV_LOG_FILE}
echo "APPD_APPID $APPD_APPID" >>${ENV_LOG_FILE}
echo "APPD_DC $APPD_DC" >>${ENV_LOG_FILE}
echo "APPD_ZONE $APPD_ZONE" >>${ENV_LOG_FILE}
echo "APPDYNAMICS_AGENT_APPLICATION_NAME $APPDYNAMICS_AGENT_APPLICATION_NAME" >>${ENV_LOG_FILE}
echo "APPDYNAMICS_AGENT_TIER_NAME $APPDYNAMICS_AGENT_TIER_NAME" >>${ENV_LOG_FILE}
echo "APPDYNAMICS_AGENT_NODE_NAME $APPDYNAMICS_AGENT_NODE_NAME" >>${ENV_LOG_FILE}
echo "CATALINA_OPTS $CATALINA_OPTS" >>${ENV_LOG_FILE}
echo "JAVA_OPTS $JAVA_OPTS" >>${ENV_LOG_FILE}

exec /usr/local/tomcat/bin/catalina.sh run
