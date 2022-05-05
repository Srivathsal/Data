POD=`kubectl get pods | grep reminder | awk '{print $1;}'`
echo "Pod is $POD"
CONTAINER=`kubectl get pods $POD -o jsonpath='{.spec.containers[*].name}'`
echo "Pod is $POD and container is $CONTAINER, logs ..."

kubectl logs  --since=1h  $POD #$CONTAINER
