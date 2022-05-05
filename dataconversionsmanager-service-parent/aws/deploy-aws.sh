
WORKSPACE=..
DATA_CENTER=usw2
STACK_NAME=reminder
CFG_ENV=usw2
APP_ENV=qal
SERVICE_VERSION=1.0.0.731e

function print_header {
  echo $'\n'"###################################################################"
  echo "$1"
  echo "###################################################################"
}

print_header "Removing old stack service 'docker service rm reminder-usw2-qal_app'"
docker stack rm reminder-usw2-qal_app &>/dev/null
docker service rm reminder-usw2-qal_app &>/dev/null

print_header "Pulling docker image"
docker pull docker.artifactory.a.intuit.com/accountant/accounting/reminders-service:${SERVICE_VERSION}

print_header "Docker images on manager"
docker images

#echo "Setting secrets"
#docker secret create services_reminders_service_app_secret  ${PWD}/scrts/appsecret


print_header "Deploying to $DATA_CENTER..."
export DC=$DATA_CENTER

print_header "docker stack deploy -c stack.yaml ${STACK_NAME}-${CFG_ENV}-${APP_ENV}"
docker stack deploy -c stack.yaml ${STACK_NAME}-${CFG_ENV}-${APP_ENV}

sleep 1
docker stack ps reminder-usw2-qal
docker service ps reminder-usw2-qal_app
docker ps
