package endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/")
public class Root {
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMyResources() {
		
		return Response.status(Status.BAD_REQUEST).entity(Status.BAD_REQUEST.toString()).build();
	}
	

}