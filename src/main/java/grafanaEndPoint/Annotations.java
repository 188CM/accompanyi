package grafanaEndPoint;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/annotations")
public class Annotations {
	
	@Context
	private HttpServletRequest httpRequest;

	@GET
    public Response annotationOptions() {		
		return Response.noContent()
                .header("Access-Control-Allow-Headers", "accept, content-type")
                .header("Access-Control-Allow-Methods", "POST")
                .header("Access-Control-Allow-Origin", "*")
                .build();
	}
	
	@POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response post() {		
		return Response.status(Status.OK).entity(Status.OK.toString()).build();
	}
	


}