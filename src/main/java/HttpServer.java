

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class HttpServer {
    public static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
   	
    	 Server server = new Server(PORT); 	
    	 
         ServletHolder rootServletHolder = new ServletHolder(org.glassfish.jersey.servlet.ServletContainer.class);
         rootServletHolder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
         rootServletHolder.setInitParameter("jersey.config.server.provider.packages", "grafanaEndPoint");// set EndPoint
         rootServletHolder.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
                    	 
    	 ServletHolder servletHolder = new ServletHolder(org.glassfish.jersey.servlet.ServletContainer.class);
         servletHolder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
         servletHolder.setInitParameter("jersey.config.server.provider.packages", "oldEndpoint");// set EndPoint
         servletHolder.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
         
         
         
         ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
         server.setHandler(contextHandler);
         
         contextHandler.addServlet(rootServletHolder, "/grafana/*");
         contextHandler.addServlet(servletHolder, "/api/*");

    	
         
         try {
             server.start();
             server.join();
         } catch (Exception ex) {
             System.exit(1);
         }
    }
    
    
    
    
}
