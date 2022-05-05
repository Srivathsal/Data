#!/bin/bash
echo "Setting docker-registry secret"
kubectl create secret \
    docker-registry regsecret \
    --docker-server=docker.artifactory.a.intuit.com \
    --docker-username=takyiu_li@intuit.com \
    --docker-password=AKCp5aTkx9TdBKyU66JWDU9B8a6b6pQfuNDqKfBz2g8V5PZ3L1DrYVYN4M1EQYNXHxtBVZsiD \
    --docker-email=takyiu_li@intuit.com
echo "Created artifcatory Secret"

kubectl create secret \
    generic reminder-idps \
    --from-file=../../docker/secrets/reminders-service-idps.pem
echo "Done adding idps secret"
# create secret <.type> <.secret-name> --from-file=../../docker/secrets/reminders-service-idps.pem
#   kubectl create secret generic my-secret --from-env-file=path/to/bar.env
