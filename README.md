# TestHTTPProxyServlet

Test for the Smiley's HTTP Proxy Servlet : https://github.com/mitre/HTTP-Proxy-Servlet with **Spring Boot – Embedded servlet containers (tomcat)**

## Run

 - `mvn clean package`
 - `java -Dserver.port=8888 -jar target/TestHTTPProxyServlet-1.0-SNAPSHOT.jar`

Gateway test for `/users/*` to `http://localhost:8090/users` with this application.yml spring boot configuration file :

```
proxy:
    cgi:
        servlet_url: /users/*
        target_url: http://localhost:8090/users
        logging_enabled: true
``` 


