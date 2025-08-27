package org.dandroid.jwtapp.config;

import jakarta.inject.Singleton;
import org.dandroid.jwtapp.repository.UserRepository;
import org.dandroid.jwtapp.security.JwtTokenUtil;
import org.dandroid.jwtapp.service.AuthService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ConfigurationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        // Esto hace que JwtTokenUtil sea inyectable
        bindAsContract(JwtTokenUtil.class).in(Singleton.class);
        bindAsContract(AuthService.class).in(Singleton.class);
        bindAsContract(UserRepository.class).in(Singleton.class);// O RequestScoped.class según necesites
    }



    // jboss, jetty, tomcat, glassfish, undertow, wildfly,apache

    // Servlet (http), Security (JWT,Filter, CORS), Logging (Logback, SLF4J), JAX-RS (Jersey, REST), CDI (Contexts and Dependency Injection), JSON (Jackson, Gson), JPA (Hibernate, EclipseLink), Testing (JUnit, Mockito)

}
