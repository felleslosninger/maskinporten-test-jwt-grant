FROM eid-jenkins02.dmz.local:8081/spring-boot-base:latest

COPY /jar/jwt-grant-performance.jar /usr/local/webapps/application.jar
COPY entrypoint.sh /entrypoint.sh
COPY java.security /usr/local/openjdk-8/jre/lib/security/java.security

HEALTHCHECK --interval=10s --start-period=10s --retries=15 \
   CMD { curl -f --max-time 10 http://localhost:8080/actuator/health; } || exit 1

ENTRYPOINT ["sh", "/entrypoint.sh"]