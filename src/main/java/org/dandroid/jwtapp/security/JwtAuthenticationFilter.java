package org.dandroid.jwtapp.security;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthenticationFilter implements ContainerRequestFilter {

    @Inject
    private JwtTokenUtil jwtTokenUtil;

    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String path = requestContext.getUriInfo().getPath();

        if(path.contains("login") || path.contains("check")){return;}

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Si no tiene el header de autorizacion, aborta
        if (authorizationHeader == null || !authorizationHeader.startsWith(AUTHENTICATION_SCHEME)) {abortWithUnauthorized(requestContext);return;}


        // Extrae el token del header de autorizacion
        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();


        try {
            String username = JwtTokenUtil.getUsernameFromToken(token);
            System.out.println("Username from token: " + username);
            String password = JwtTokenUtil.getPasswordFromToken(token);
            System.out.println("Password from token: " + password);
            if (username == null || !JwtTokenUtil.validateToken(token)) {abortWithUnauthorized(requestContext);}
        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }


    }

    private  void abortWithUnauthorized(ContainerRequestContext requestContext){
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .header("WWW-Authenticate", "Bearer realm=jwt")
                .entity("acceso no autorizado").build());
    }
}
