package org.dandroid.jwtapp.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.dandroid.jwtapp.security.JwtTokenUtil;

@Path("/auth")
public class AuthResource {
    @Inject
    private JwtTokenUtil jwtTokenUtil;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login (LoginRequest loginRequest){
        if("admin".equals(loginRequest.getUsername()) && "password".equals(loginRequest.getPassword())){
            String token = jwtTokenUtil.generateToken(loginRequest.getUsername());
            return Response.ok(new TokenResponse(token)).build();
        }else{
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Credenciales invalidas")
                    .build();
        }
    }

    public static class LoginRequest{
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class TokenResponse{
        private String token;

        public TokenResponse(String token){
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
