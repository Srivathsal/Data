# Reminder Service
============
For Local Testing
docker stack deploy -c aws-stack.yaml reminder-usw2-qal
docker pull docker.artifactory.a.intuit.com/accountant/accounting/reminders-service:1.0.0.731e

Working Commands
docker service ls
docker service ps reminder-usw2-qal_app
docker service rm reminder-usw2-qal_app
docker inspect reminder-usw2-qal_app
docker login docker.artifactory.a.intuit.com -u yexin_huang@intuit.com -p AKCp5Zjpq5LuPnmoGRKdCNXPg9wCxMjz3gzRBUpkNmu72F7HV2MWiZUvEnxk5oKxCXcDkZ2au

docker run --env CFG_ENV=dev --env CFG_SERVICE_NAME=pcgreminders --env APP_ID=Intuit.tax.services.reminders --env APP_SECRET='preprdZgM0LzAIs6ga0wspoJMGlhkfNGvQxsPXxg' --env CFG_DC=qdc --env DC_VERSION=1.0.3 -ti "reminders-service:latest"


docker secret create services_reminders_service_app_secret  /Users/amattey/dev/intuit/reminders-service/deploy/docker/run/secrets/appsecret
docker secret create services_reminders_service_idps_secret reminders-service-idps.pem

docker build .
docker tag 4f9d97744329 docker.artifactory.a.intuit.com/accountant/accounting/reminders-service:1.0.0.731e
docker push docker.artifactory.a.intuit.com/accountant/accounting/reminders-service:1.0.0.731e
http://pcg-services-alb-234312973.us-west-2.elb.amazonaws.com:8080/reminders-service/support/v1/health/full


docker pull pcgregistry.intuit.com/services/cassandra-rest:latest
docker tag c8e5b1fe0de3 docker.artifactory.a.intuit.com/accountant/accounting/cassandra-rest:latest
docker push docker.artifactory.a.intuit.com/accountant/accounting/cassandra-rest:latest

docker pull docker.artifactory.a.intuit.com/accountant/accounting/cassandra-rest:latest
docker images | grep cassandra
docker run -p 8081:8080 -d c8e5b1fe0de3


# Next Steps
============
DONE - 1. Splunk
DONE FOR NOW - 2. Cassandra Rest 
    ISSUE - bundled with reminders, need a brand new stack deployment 
2.1 Regression to Pass
    ISSUE - When I run reminders regression, the container is not able to handle the traffic and dies after 10 mins, seems like upgrading to large seems like a solution, but need help understanding what is the correct approach
    ISSUE - Noticed that reminders service is not starting on docker worker, it works only on manager, could be a stack deployment issue or docker secrets issue
DONE - 3. Check if email is really sent 
4. Use Config V2 using intuit-cfg GO Script
5. What is correct way of laying docker secrets
6. Deployment Automation (Currently using deploy-aws.sh)
7. Gateway AWS On-boarding for QAL
8. Tear down and rebuild using Latest AMI via Cloud Formation Script
9. Two way routing between IHP and AWS
10. AWS 3rd DC for Cassandra
11. Splunk Target State - PCG Or PI in AWS
12. base Services 


# config file 
============
~ cat ~/.ssh/config
Host bastion
HostName 35.163.158.252
User ec2-user
Identityfile /Users/hhira1/.ssh/hhira-intuit-developer-keys.pem
port 22

Host dockermgr1a
Hostname 10.10.0.23
User ec2-user
Identityfile /Users/hhira1/.ssh/hhira-intuit-developer-keys.pem
Port 22
ProxyCommand ssh -W %h:%p bastion

Host dockerwrkr1a
Hostname 10.10.0.60
User ec2-user
Identityfile /Users/hhira1/.ssh/hhira-intuit-developer-keys.pem
Port 22
ProxyCommand ssh -W %h:%p bastion           


docker login docker.artifactory.a.intuit.com -u yexin_huang@intuit.com -p AKCp5Zjpq5LuPnmoGRKdCNXPg9wCxMjz3gzRBUpkNmu72F7HV2MWiZUvEnxk5oKxCXcDkZ2au
docker login docker.artifactory-preprod.a.intuit.com -u amar_mattey@intuit.com -p AKCp5ZjpeRsdnRpzbQVAAVJqpAayav5p2FbHN4s1EmTSqpFqvbD5VbNJuFAboRHfNjgQWwdFq


docker pull docker.artifactory-preprod.a.intuit.com/oicp/rhel7
============

Latest Release Notes : https://github.intuit.com/PTG/reminders-service/webapp/src/main/webapp/ReleaseNotes.md

This is a Reminders service. Through this service you can send reminders to your clients on demand or on a future date.

## Getting Started

To get you started you need to install the dependencies:

### Prerequisites
1. The project is configured to develop on Mac only.
2. Java8 is required
3. Maven 3.2.5 is required
4. docker is required

### Install Dependencies
* Install Docker from https://www.docker.com/docker-toolbox

### Setup project
* Clone from https://github.intuit.com/PTG/reminders-service
* In terminal, go to directory reminders-service/docker
* Start docker machine
```
docker-machine start default
```
* Set docker machine env
```
execute docker-machine env default
```
### Compile and start reminder-service
* In terminal, go to directory reminders-service/docker
* Run this command to configure your shell:
```
eval "$(docker-machine env default)"
```
** Alternately, you can add 'eval "$(docker-machine env default)"' to .bash_profile
* Start reminder service docker machine
```
./oneclick.sh 
```

### Frisby test
* In terminal, go to directory reminders-service/docker
* Run this command to configure your shell:
```
eval "$(docker-machine env default)"
```

* Start Frisby docker machine
```
./oneclick.sh frisby
```
* cd reminders
* Run test
```
jasmine-node --config env localhost [any_spec.js]

```
  
  
### IDPS onboarding URL
https://ReminderService-Playground-UQ214V.pd.idps.a.intuit.com

docker run -p 8095:8080 -p 18095:8000 -v ~/docker/reminders-service/logs:/usr/local/tomcat/logs --env CFG_ENV=e2e --env CFG_SERVICE_NAME=pcgreminders --env APP_ID=Intuit.tax.services.reminders --env APP_SECRET='preprdZgM0LzAIs6ga0wspoJMGlhkfNGvQxsPXxg' --env CFG_DC=qdc --env DC_VERSION=1.0.3 -it reminders-service:latest

### Invitations
create new column notification_properties (Only on Entity not on DTO)
Save reminders offline ticket
### Design Decision (Oct 24 2017)
As IUS does not support GET on invitations to verify if an invitation is accepted, we will use the 
GET /v1/realms/{realmId}/customers and verify if GlobalIntuit Id is available.

If the tax payer is already associated with the realm as a client, the invitation cannot be created
If the tax payer did not accept the invitation, the globalintuit id for this realm would be null
As current tax colloberation does not have a reject inviation flow we will follow this for now
Also according to Almira the ticket is valid for 90 days we need to write some tests to see if the ticket is valid.
Email Subject :  Help with Invitations Verification and Resend
https://wiki.intuit.com/pages/viewpage.action?spaceKey=Identity&title=Invitation+Technical+Design

https://jira.intuit.com/browse/IIEA-660