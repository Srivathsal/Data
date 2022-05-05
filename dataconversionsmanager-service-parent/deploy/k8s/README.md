# deploying
`helm install --debug --set image.repository=docker.artifactory.a.intuit.com/accountant/accounting/reminders-service --set image.tag=1.1.0.747 --set environment.CFG_ENV=qal --set environment.CFG_TAG=1.1.0.747 --name reminder-qal app`
