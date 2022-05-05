mvn clean install -DskipTests=true
docker build .
echo docker tag 4f9d97744329 docker.artifactory.a.intuit.com/accountant/accounting/dataconversionsmanager-service:1.0.0.731e
echo docker push docker.artifactory.a.intuit.com/accountant/accounting/dataconversionsmanager-service:1.0.0.731e
