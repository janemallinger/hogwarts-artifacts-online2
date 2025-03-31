package edu.tcu.cs.hogwartsartifactsonline2.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.nio.file.AccessDeniedException;


@Component
public class CustomBearerTokenAccessDeniedHandler implements AccessDeniedHandler{

    private final HandlerExceptionResolver resolver;

    public CustomBearerTokenAccessDeniedHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        this.resolver.resolveException(request, response, null, accessDeniedException);

    }
}
