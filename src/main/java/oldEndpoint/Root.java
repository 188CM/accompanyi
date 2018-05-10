package oldEndpoint;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/")
public class Root {
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response get() {		
		return Response.status(Status.OK).entity(Status.OK.toString()).build();
	}
	
	
	@POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response post() {		
		return Response.status(Status.OK).entity(Status.OK.toString()).build();
	}

}