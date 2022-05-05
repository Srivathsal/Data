#!/usr/bin/env bash
POD=`kubectl get pods | grep reminder | awk '{print $1;}'`
echo $POD
kubectl describe pod $POD
