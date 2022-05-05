#!/bin/bash -x

cd /tmp
curl "https://s3.amazonaws.com/aws-cli/awscli-bundle-1.19.112.zip" -o "/tmp/awscli-bundle.zip"
unzip /tmp/awscli-bundle.zip
/tmp/awscli-bundle/install -b /tmp/aws
