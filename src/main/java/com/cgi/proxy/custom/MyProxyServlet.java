package com.cgi.proxy.custom;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.mitre.dsmiley.httpproxy.ProxyServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class MyProxyServlet extends ProxyServlet {

    @Override
    public HttpResponse doExecute(HttpServletRequest servletRequest, HttpServletResponse servletResponse, HttpRequest proxyRequest) throws IOException {
        if (this.doLog) {
            this.doCustomLog(servletRequest);
        }

        return super.doExecute(servletRequest, servletResponse, proxyRequest);
    }


    private void doCustomLog(HttpServletRequest request) {

        // see for other log : https://gist.github.com/sleroy/83bb5d3c2b55282b02d3b19ade650331
        // use Actuator (There's an endpoint mapped to /trace (SB1.x))
        // Tuto : https://www.baeldung.com/spring-boot-actuators#boot-1x-actuator

        StringBuilder builderHeaders = new StringBuilder();
        builderHeaders.append("Headers Name - ");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            builderHeaders.append("[").append(headerName).append("] = [").append(request.getHeader(headerName)).append("] / ");
        }
        this.log(!builderHeaders.toString().isEmpty() ? builderHeaders.toString() : "No Header !");

        StringBuilder builderParams = new StringBuilder();
        builderParams.append("Parameters Name - ");
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            builderParams.append("[").append(paramName).append("] = [").append(request.getParameter(paramName)).append("] / ");
        }
        this.log(!builderParams.toString().isEmpty() ? builderParams.toString() : "No Param !");
    }
}
