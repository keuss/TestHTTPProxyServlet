package com.keuss.proxy;

import com.keuss.proxy.custom.MyProxyServlet;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ProxyConfiguration implements EnvironmentAware {

    // It may be the case that Spring Boot (or Spring MVC) is consuming the servlet input stream before the servlet gets it, which is a problem.
    // if see https://github.com/mitre/HTTP-Proxy-Servlet/issues/83#issuecomment-307216795

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new MyProxyServlet(), propertyResolver.getProperty("servlet_url"));
        servletRegistrationBean.addInitParameter("targetUri", propertyResolver.getProperty("target_url"));
        servletRegistrationBean.addInitParameter(ProxyServlet.P_LOG, propertyResolver.getProperty("logging_enabled", "true"));
        return servletRegistrationBean;
    }

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "proxy.keuss.");
    }

}
