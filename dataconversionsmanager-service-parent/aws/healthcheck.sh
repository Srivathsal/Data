CID=$(docker ps --latest --quiet)
echo Found Container with Id $CID
echo docker exec -it $CID curl -X GET http://localhost:8080/reminders-service/support/v1/health/full
docker exec -it $CID curl -X GET http://localhost:8080/reminders-service/support/v1/health/full