#!/bin/bash
helm del --purge services-remindersvc-dev
helm install --debug   \
    --set image.repository=docker.artifactory.a.intuit.com/accountant/accounting/reminders-service \
    --set image.tag=1.0.0.747 \
    --set environment.CFG_ENV=qal \
    --set environment.CFG_TAG=master \
    --set environment.CFG_DC=qdc \
    --set environment.DC_VERSION=1.0.8 \
    --set environment.C_LIMIT_CPU=\"1\" \
    --set environment.APP_ENV=env1 \
    --set environment.CFG_SERVICE_NAME=pcgreminders \
    --set environment.APP_ID=Intuit.tax.services.reminders \
    --set environment.APP_SECRET=preprdZgM0LzAIs6ga0wspoJMGlhkfNGvQxsPXxg \
    --name services-remindersvc-dev \
    ./app
