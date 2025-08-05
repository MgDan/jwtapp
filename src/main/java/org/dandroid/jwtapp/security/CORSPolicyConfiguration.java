package org.dandroid.jwtapp.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




@WebFilter(urlPatterns = "/*") // Aplica el filtro a todas las rutas
public class CORSPolicyConfiguration  implements Filter {

    public static final String CORS_ORIGIN = "Access-Control-Allow-Origin";
    public static final String CORS_METHODS = "Access-Control-Allow-Methods";
    public static final String CORS_HEADERS = "Access-Control-Allow-Headers";
    public static final String CORS_MAX_AGE = "Access-Control-Max-Age";

    public static final String ALLOWED_ORIGINS = "http://localhost:5051";
    public static final String ALLOWED_METHODS = "GET, POST, PUT, DELETE, OPTIONS";
    public static final String ALLOWED_HEADERS = "Content-Type, Authorization";
    public static final int SC_FORBIDDEN = 403; // Código de estado HTTP para acceso prohibido
    public static final int MAX_AGE_SECONDS = 3600; // 1 hora

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException, jakarta.servlet.ServletException {

        // Verifica que la solicitud y respuesta sean del tipo HttpServletRequest y HttpServletResponse
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Obtener el origen de la solicitud
        String origin = httpRequest.getHeader("Origin");

        // Imprime el origen de la solicitud en la consola para depuración
        System.out.println("CORS Filter triggered  Origin: " + origin);

        // Verifica si el origen de la solicitud es permitido
        if (origin != null && !ALLOWED_ORIGINS.equals(origin)) {
            // Si el origen no es permitido, establece el código de estado HTTP 403 y devuelve un mensaje de error
            System.out.println("Invalid Request Origin: " + origin);
            httpResponse.setStatus(SC_FORBIDDEN);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"error\":\"Invalid origin request\"}");
            return;
        }

        httpResponse.setHeader(CORS_ORIGIN, ALLOWED_ORIGINS);
        httpResponse.setHeader(CORS_METHODS, ALLOWED_METHODS);
        httpResponse.setHeader(CORS_HEADERS, ALLOWED_HEADERS);
        httpResponse.setHeader(CORS_MAX_AGE, String.valueOf(MAX_AGE_SECONDS));
        chain.doFilter(request, response);
    }







}
