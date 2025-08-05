package org.dandroid.jwtapp.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.dandroid.jwtapp.security.JwtTokenUtil;

@Path("/test")
public class TestResource {

        @GET
        @Path("/check")
        @Produces(MediaType.APPLICATION_JSON)
        public Response test() {
            return Response.ok("{\"message\": \"Auth endpoint is working\"}").build();
        }


        @GET
        @Path("/protected")
        @Produces(MediaType.APPLICATION_JSON)
        public Response protectedResource(@HeaderParam("Authorization") String authHeader) {

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        return Response.status(Response.Status.UNAUTHORIZED).entity("No token provided").build();
                }

                String token = authHeader.substring("Bearer ".length());
                try {
                        String username = JwtTokenUtil.getUsernameFromToken(token);
                        System.out.println("Username from token: " + username);
                        String password = JwtTokenUtil.getPasswordFromToken(token);
                        System.out.println("Password from token: " + password);
                        if (username == null || !JwtTokenUtil.validateToken(token)) {
                                return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
                        }
                        // If valid, return protected data
                        return Response.ok("{\"message\":\"Hello, " + username + "! This is a protected resource.\"}").build();
                } catch (Exception e) {
                        return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
                }
        }


        @GET
        @Path("/another-protected")
        @Produces(MediaType.APPLICATION_JSON)
        public Response anotherProtectedResource() {
                // No need to check token here, filter already did it
                return Response.ok("{\"message\":\"This is another protected resource.\"}").build();
        }

}
