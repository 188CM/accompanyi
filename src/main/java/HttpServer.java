

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class HttpServer {
    public static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
    	
    	 Server server = new Server(PORT); 	
         ServletHolder servletHolder = new ServletHolder(org.glassfish.jersey.servlet.ServletContainer.class);
         servletHolder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
         servletHolder.setInitParameter("jersey.config.server.provider.packages", "endpoint");// set EndPoint
         servletHolder.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
         ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
         server.setHandler(contextHandler);
         
         contextHandler.addServlet(servletHolder, "/api/*");
    	
         
         try {
             server.start();
             server.join();
         } catch (Exception ex) {
             System.exit(1);
         }
    }
    
    
    
    
}
