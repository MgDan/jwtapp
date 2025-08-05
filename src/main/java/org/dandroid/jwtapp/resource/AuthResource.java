package org.dandroid.jwtapp.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
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

            String token;
            try {
                // Simulamos una validación de usuario
                // Aquí podrías llamar a un servicio de autenticación real
                // o verificar contra una base de datos.
                token = jwtTokenUtil.generateToken(loginRequest.getUsername(),loginRequest.getPassword());
                System.out.println("Token generado: " + token);


            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error al procesar la solicitud")
                        .build();
            }

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
