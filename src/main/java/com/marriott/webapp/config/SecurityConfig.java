package com.marriott.webapp.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

@Configuration
class SecurityConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8081")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        //.allowedHeaders("Header1", "Header2", "Header3")
                        //.exposedHeaders("Header1", "Header2")
                        .allowCredentials(true).maxAge(3600);
            }
        };
    }

    @Bean
    public Filter securityHeadersFilter() {

        return new Filter() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
                var httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader("Content-Security-Policy", "default-src 'self'");
                chain.doFilter(request, response);
            }

            @Override
            public void init(FilterConfig filterConfig) throws ServletException {
                // Initialization code can be put here
            }

            @Override
            public void destroy() {
                // Destruction code can be put here
            }
        };
    }

}

