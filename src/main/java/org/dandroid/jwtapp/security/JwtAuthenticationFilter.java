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
        if(requestContext.getUriInfo().getPath().contains("login")){
            return;
        } //si es el login sale del filtro

        // Si no tiene el header de autorizacion, aborta
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if(authorizationHeader == null || !authorizationHeader.startsWith(AUTHENTICATION_SCHEME)){
            abortWithUnauthorized(requestContext);
        } // Si no tiene el header de autorizacion, aborta

        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        try {
            if(!jwtTokenUtil.validateToken(token) ){
                abortWithUnauthorized(requestContext);
            }
        }catch (Exception e){
            abortWithUnauthorized(requestContext);
        }
    }

    private  void abortWithUnauthorized(ContainerRequestContext requestContext){
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .header("WWW-Authenticate", "Bearer realm=jwt")
                .entity("acceso no autorizado").build());
    }
}
