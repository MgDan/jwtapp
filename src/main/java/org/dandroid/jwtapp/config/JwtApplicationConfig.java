package org.dandroid.jwtapp.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.dandroid.jwtapp.listener.JerseyInitializer;
import org.dandroid.jwtapp.resource.AuthResource;
import org.dandroid.jwtapp.security.JwtAuthenticationFilter;
import org.dandroid.jwtapp.security.JwtTokenUtil;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.ResourceConfig;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import jakarta.inject.Singleton;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationPath("/api")
public class JwtApplicationConfig extends ResourceConfig {

    ConfigurationBinder configurationBinder = new ConfigurationBinder();

/*
    public JwtApplicationConfig() {
        packages("org.dandroid.jwtapp");
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        register(org.glassfish.jersey.jackson.JacksonFeature.class);
    }*/
    public JwtApplicationConfig(){
        super(); // Llama al constructor padre primero
        configure();
    }

    private void configure() {
        // Configuración de recursos
        packages("org.dandroid.jwtapp");


        register(RolesAllowedDynamicFeature.class);
        // register(JerseyInitializer.class);
        register(JwtAuthenticationFilter.class);
        register(configurationBinder);

        /*
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                // Esto hace que JwtTokenUtil sea inyectable
                bindAsContract(JwtTokenUtil.class)
                        .in(Singleton.class); // O RequestScoped.class según necesites
            }
        });

         */
    }
}
