#!/usr/bin/env bash

SERVICE_DIR=$(pwd)/..
SERVICE_ID=dataconversionsmanager
SERVICE_NAME=dataconversionsmanager-service
SERVICE_LOG=$SERVICE_DIR/docker/logs/$SERVICE_NAME.log
SERVICE_URL=http://localhost:8080/${SERVICE_NAME}
DOCKER_COMPOSE_FILE_NAME=$SERVICE_DIR/docker-compose.yml
ONECLICK_DIR=~/.oneclick

echo "Initializing oneclick"

if [ -d "$ONECLICK_DIR" ]
then
    echo "$ONECLICK_DIR directory  exists!"
    cd $ONECLICK_DIR
    git pull
else
    mkdir -p $ONECLICK_DIR
    git clone git@github.intuit.com:PTG/oneclick.git $ONECLICK_DIR
    echo "Successfully cloned"
fi

source $ONECLICK_DIR/scripts/oneclick-base.sh
oneclick
