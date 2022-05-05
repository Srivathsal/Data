FROM docker.artifactory.a.intuit.com/accountant/accounting/services/services-m2repo-cache/dataconversionsmanager-service:1.0.0 AS BUILD

ARG BUILD_NUMBER=SNAPSHOT
ARG SKIP_TEST=false
ARG SOURCE_BRANCH=

COPY . /app
WORKDIR /app

# TODO Move it to some common documentation. The decision to pass in source_branch along with the build number as the AWS codepipeline will have
# different jobs for different branches.
RUN ./build/build.sh ${BUILD_NUMBER}-${SOURCE_BRANCH}

FROM docker.artifactory.a.intuit.com/accountant/accounting/pcg/tomcat7:latest AS RELEASE
MAINTAINER PCG-PDServicesEngineers@intuit.com


USER root

# These labels are auto populated by Jenkins and will be overwritten
LABEL date=$date
LABEL git_commit=$git_commit

# Please add as many LABEL key=value to best describe your image
LABEL Description="Docker image for Data Conversions Manager Service"
# Service Name is a requirement.
LABEL service_name=dcmanager-service

# This is the Area where all your application related command go
COPY --from=BUILD /app/webapp/target/*.war /usr/local/tomcat/webapps/
COPY --from=BUILD /app/webapp/src/main/resources/competitor-software-configuration.xml /usr/local/tomcat/webapps-config/
COPY --from=BUILD /app/webapp/src/main/resources/competitor-software-configuration-protax.xml /usr/local/tomcat/webapps-config/
COPY --from=BUILD /app/webapp/src/main/resources/intuit-software-configuration.xml /usr/local/tomcat/webapps-config/
COPY --from=BUILD /app/deploy/docker/config/server.xml /usr/local/tomcat/conf/server.xml
COPY --from=BUILD /app/version_number.txt /tmp/version_number.txt
COPY --from=BUILD /app/deploy/docker/crypto/* /usr/java/default/lib/security/
COPY --from=BUILD /app/deploy/docker/*.sh /tmp/
COPY --from=BUILD /app/asset-info.properties /tmp/

RUN mkdir /home/app
RUN chmod 755 /tmp/apm_config.sh /tmp/proxy.sh /tmp/apm.sh /tmp/start.sh
#RUN mkdir /usr/local/cto-tomcat-7/logs
RUN chown -R app:app /tmp /usr/local/cto-tomcat-7 /usr/java/default/lib/security/ /home/app

# Ports to be Exposed
EXPOSE 8080

# Healthcheck https://docs.docker.com/engine/reference/builder/#/healthcheck
#HEALTHCHECK --interval=1m --timeout=3s \
#  CMD curl -f http://localhost:8080/dataconversionsmanager-service/v1/health/full || exit 1

# This is the command used to start up tomcat inside the container
# as user app
USER app
CMD ["/tmp/start.sh"]
