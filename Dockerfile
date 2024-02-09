FROM tomcat:10

COPY target/spring-project-1.war /usr/local/tomcat/webapps/

ENTRYPOINT ["catalina.sh", "run"]