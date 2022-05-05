#!/bin/bash -e

AWS_BIN=/usr/bin/aws 

if [ ! -f $AWS_BIN ]; then
    curl "https://s3.amazonaws.com/aws-cli/awscli-bundle.zip" -o "awscli-bundle.zip"
    unzip awscli-bundle.zip 
    awscli-bundle/install -b /usr/bin/aws
fi

aws sts assume-role --role-arn arn:aws:iam::135026383220:role/CrossAccount_IBP-S3Push --role-session-name s3-access-pcg > /dev/null 2>&1

mkdir ~/.aws

tee ~/.aws/config <<-EOF
[profile s3-access-pcg]
role_arn = arn:aws:iam::135026383220:role/CrossAccount_IBP-S3Push
credential_source=Ec2InstanceMetadata
EOF

echo "[{\"name\":\"container_name\",\"imageUri\":\"image_URI\"}]'" | sed -e 's/image_URI/'${DOCKER_TAG_WITH_VERSION}'/' > imagedefinitions.json
cat imagedefinitions.json
tar -cvf reminders-service.tar imagedefinitions.json

aws s3 cp ./reminders-service.tar s3://reminders-service/releases/reminders-service.tar --profile  s3-access-pcg
