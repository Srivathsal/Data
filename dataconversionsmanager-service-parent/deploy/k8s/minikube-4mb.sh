#!/bin/bash
#minikube stop
#minikube delete
#minikube --memory 4096 --cpus 2 start
minikube stop && minikube start --cpus 4 --memory 8192

