##########################################################################################
############### BLOCK NEEDED FOR APPD ####################################################

source /tmp/apm_config.sh

export SET_UNIQUE_HOST_ID="true"
export USE_DEV_TRIAL_CONTROLLER="false"

if [[ "$SET_UNIQUE_HOST_ID" == "true" && "${CFG_ENV}" != "prd" ]]; then
   echo "INFO :: Waldo Starting"
   UNIQUE_HOST_ID=$(curl -k -s -m 5 -X POST -d "{\"id\":\"$HOSTNAME\"}" https://ptgops.intuit.net/hostname | awk -F ":" '{ print $3 }' | awk -F "," '{ print $1 }' | sed -e 's/"//g')
   if [[ "$UNIQUE_HOST_ID" =~ "Invalid" ]] || [[ "$UNIQUE_HOST_ID" =~ "Could not find ID" ]]; then
      echo "ERROR: Waldo Returned: \"$UNIQUE_HOST_ID\" for host $HOSTNAME .."
      UNIQUE_HOST_ID=""
   else
      export UNIQUE_HOST_ID=$UNIQUE_HOST_ID
      echo "INFO: Waldo returned: \"$UNIQUE_HOST_ID\" for host $HOSTNAME .."
   fi
fi

if [[ ${CFG_ENV} == "prd" ]]; then
   export APPDYNAMICS_CONTROLLER_HOST_NAME="intuit-pcg-prod.saas.appdynamics.com"
   export APPDYNAMICS_CONTROLLER_PORT="443"
   export APPDYNAMICS_CONTROLLER_SSL_ENABLED="true"
   export APPDYNAMICS_AGENT_ACCOUNT_NAME="intuit-pcg-prod"
   export APPDYNAMICS_AGENT_ACCOUNT_ACCESS_KEY="vli5re846bts"
else
   if [[ "$USE_DEV_TRIAL_CONTROLLER" == "true" ]]; then
      export APPDYNAMICS_CONTROLLER_HOST_NAME="intuittrial.saas.appdynamics.com"
      export APPDYNAMICS_CONTROLLER_PORT="443"
      export APPDYNAMICS_CONTROLLER_SSL_ENABLED="true"
      export APPDYNAMICS_AGENT_ACCOUNT_NAME="intuittrial"
      export APPDYNAMICS_AGENT_ACCOUNT_ACCESS_KEY="kls8s5cdys0o"
   else
      export APPDYNAMICS_CONTROLLER_HOST_NAME="intuit-pcg-dev.saas.appdynamics.com"
      export APPDYNAMICS_CONTROLLER_PORT="443"
      export APPDYNAMICS_CONTROLLER_SSL_ENABLED="true"
      export APPDYNAMICS_AGENT_ACCOUNT_NAME="intuit-pcg-dev"
      export APPDYNAMICS_AGENT_ACCOUNT_ACCESS_KEY="ac4383ac-f015-4b69-bccd-64f5af12852b"
   fi
fi

echo "Using AppD as APM"

if [[ "$CFG_DC" == "qdc" ]]; then
  export APPD_DC=qcy
  export APPD_ZONE=c
elif [[ "$CFG_DC" == "lvdc" ]]; then
  export APPD_DC=las
  export APPD_ZONE=a
elif [[ "$CFG_DC" == "usw2" ]]; then
  export APPD_DC=aws
  export APPD_ZONE=usw2
fi

# SET  APPD VARS
export APPDYNAMICS_AGENT_APPLICATION_NAME=pcg_$CFG_ENV
export APPDYNAMICS_AGENT_TIER_NAME=${APPD_APPID}_${APPD_DC}
export APPDYNAMICS_AGENT_NODE_NAME=${APPD_APPID}_hostname:${HOSTNAME}_location:${APPD_DC}_zone:${APPD_ZONE}
if [[ ${CFG_DC} =~ .*us*. ]]; then
	echo "we are in AWS, not setting proxy"
else
    export CATALINA_OPTS="${CATALINA_OPTS} -Dappdynamics.http.proxyHost=${PROXY} -Dappdynamics.http.proxyPort=80"
fi
if [[ "$SET_UNIQUE_HOST_ID" == "true" && "$UNIQUE_HOST_ID" != "" && "$UNIQUE_HOST_ID" =~ intuit.net ]]; then
	export CATALINA_OPTS="$CATALINA_OPTS -Dappdynamics.agent.uniqueHostId=${UNIQUE_HOST_ID}"
fi

#### Enabling object tracking in AppD ####
if [[ "${USE_TOOLS}" != "" ]]; then
    echo "Downloading tools.jar..."
    curl http://apdpdnexus.corp.intuit.net/nexus/content/repositories/platform/com/oracle/tools/tools/0.0.0.0/tools-0.0.0.0.jar -o /usr/java/default/lib/lib/tools.jar
    echo "Done downloading tools.jar..."
fi
##########################################################################################
############### ENDBLOCK NEEDED FOR APPD ####################################################