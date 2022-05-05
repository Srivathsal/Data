#!/usr/bin/env bash
POD=`kubectl get pods | grep reminder | awk '{print $1;}'`
echo $POD
kubectl top pod $POD --containers
kubectl exec -it $POD /bin/bash
ls
