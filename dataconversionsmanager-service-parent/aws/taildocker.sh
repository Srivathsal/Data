CID=$(docker ps --latest --quiet)
echo Found Container with Id $CID
echo docker exec -it $CID tail -1000f /usr/local/whp-tomcat-7/logs/reminders-service.log
docker exec -it $CID tail -1000f /usr/local/whp-tomcat-7/logs/reminders-service.log