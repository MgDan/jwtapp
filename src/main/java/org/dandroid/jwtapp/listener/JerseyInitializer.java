package org.dandroid.jwtapp.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.dandroid.jwtapp.config.JpaInitializer;

@WebListener
public class JerseyInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext()
                .setInitParameter("jakarta.ws.rs.Application",
                        "org.dandroid.jwtapp.config.JwtApplicationConfig");

        JpaInitializer.init(); // Inicializa JPA
    }
}