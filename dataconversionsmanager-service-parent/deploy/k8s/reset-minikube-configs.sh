MEMORY=8192
CPUS=4

minikube delete
echo "Deleted minikube"
minikube config set cpus 4
minikube config set memory 8192

minikube config  view

echo "Reset memory to $MEMORY AND CPUS TO $CPUS "
