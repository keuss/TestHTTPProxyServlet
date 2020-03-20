package com.keuss.proxy.custom;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.mitre.dsmiley.httpproxy.ProxyServlet;

import javax.servlet.http.Cookie;
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
        // Help : https://www.baeldung.com/spring-boot-actuators#boot-1x-actuator

        // HEADERS
        StringBuilder builderHeaders = new StringBuilder();
        builderHeaders.append("\tHeaders Name - ");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            builderHeaders.append("[").append(headerName).append("] = [").append(request.getHeader(headerName)).append("] / ");
        }
        this.log(!builderHeaders.toString().isEmpty() ? builderHeaders.toString() : "No Header !");

        // PARAMS
        StringBuilder builderParams = new StringBuilder();
        builderParams.append("\tParameters Name - ");
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            builderParams.append("[").append(paramName).append("] = [").append(request.getParameter(paramName)).append("] / ");
        }
        this.log(!builderParams.toString().isEmpty() ? builderParams.toString() : "No Param !");

        // COOKIES
        if (request.getCookies() != null) {
            this.log("\tCookies - ");
            int i = 0;
            for (final Cookie cookie : request.getCookies()) {
                this.log("\tCookies number " + i);
                this.log("\tCookie.name=" + cookie.getName());
                this.log("\tCookie.comment=" + cookie.getComment());
                this.log("\tCookie.domain=" + cookie.getDomain());
                this.log("\tCookie.maxAge=" + cookie.getMaxAge());
                this.log("\tCookie.path=" + cookie.getPath());
                this.log("\tCookie.secured=" + cookie.getSecure());
                this.log("\tCookie.value=" + cookie.getValue());
                this.log("\tCookie.version=" + cookie.getVersion());
                i++;
            }
        }

        // BODY
        // FIXME get error for PUT and POST
        // see https://github.com/mitre/HTTP-Proxy-Servlet/issues/83#issuecomment-307216795
        // <ns1:XMLFault xmlns:ns1="http://cxf.apache.org/bindings/xformat"><ns1:faultstring xmlns:ns1="http://cxf.apache.org/bindings/xformat">java.net.SocketTimeoutException</ns1:faultstring></ns1:XMLFault>
        /*try {
            this.log("\tRequest Body - [" +
                    IOUtils.toString(request.getInputStream(), request.getCharacterEncoding()) + "]");
        } catch (final Exception e) {
            // ignored
        }*/
    }
}
