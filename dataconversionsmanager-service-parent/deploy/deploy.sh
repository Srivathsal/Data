cd ${WORKSPACE}

if [[ "${CFG_ENV}" == "prd" ]]; then
    UCP_ENV=prod
else
    UCP_ENV=preprod
fi

unset HTTP_PROXY HTTPS_PROXY http_proxy https_proxy
set +x

#Deploy container
export CFG_TAG=v$BUILD_VERSION_VERSION

#export AUTH_HEADER="Authorization: Intuit_IAM_Authentication intuit_appid=$DEPLOY_APP_ID, intuit_app_secret=$DEPLOY_APP_SECRET"
#curl --header "$AUTH_HEADER" https://config-e2e.api.intuit.net/v2/pcgscmbuildservice-reminderssvc.yaml | sed -e 's/__SERVICE_ENV__/preprod/g' > deploy.yaml



if [[ $CONFIG_BRANCH ]]; then
    export CFG_TAG=$CONFIG_BRANCH
fi

echo $CFG_TAG

if [ "$DATA_CENTER" == "both" ]; then
    echo "Deploying to qdc..."
    export DC=qdc

    # python intuit_appd_tier_maintenance.py ON
    cd /app/ucp_${UCP_ENV}/${DC} && eval $(<env.sh)

    echo "docker stack deploy -c deploy.yaml ${STACK_NAME}-${CFG_ENV}-${APP_ENV}"
    docker stack deploy -c ${WORKSPACE}/deploy/docker/stack.yaml ${STACK_NAME}-${CFG_ENV}-${APP_ENV}

    # python intuit_docker_deploy_wait.py
    # python intuit_appd_tier_maintenance.py OFF

    echo "Deploying to lvdc..."
    export DC=lvdc

    cd /app/ucp_${UCP_ENV}/${DC} && eval $(<env.sh)

    # python intuit_appd_tier_maintenance.py ON
    echo "docker stack deploy -c deploy.yaml ${STACK_NAME}-${CFG_ENV}-${APP_ENV}"
    docker stack deploy -c ${WORKSPACE}/deploy/docker/stack.yaml ${STACK_NAME}-${CFG_ENV}-${APP_ENV}

    # python intuit_docker_deploy_wait.py
    # python intuit_appd_tier_maintenance.py OFF
else
    echo "Deploying to $DATA_CENTER..."
    export DC=$DATA_CENTER

    # python intuit_appd_tier_maintenance.py ON
    cd /app/ucp_${UCP_ENV}/$DATA_CENTER && eval $(<env.sh)

    echo "docker stack deploy -c deploy.yaml ${STACK_NAME}-${CFG_ENV}-${APP_ENV}"
    docker stack deploy -c ${WORKSPACE}/deploy/docker/stack.yaml ${STACK_NAME}-${CFG_ENV}-${APP_ENV}

    # python intuit_docker_deploy_wait.py
    # python intuit_appd_tier_maintenance.py OFF
fi

