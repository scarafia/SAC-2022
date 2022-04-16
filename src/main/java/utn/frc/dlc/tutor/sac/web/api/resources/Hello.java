package utn.frc.dlc.tutor.sac.web.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("hello")
public class Hello {

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }

    @GET
    @Path("{name}")
    @Produces("text/plain")
    public String greet(@PathParam("name") String name) {
        return String.format("Hello, %s!", name);
    }

}
