package utn.frc.dlc.tutor.sac.web.api.resources;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

@Path("/ping")
public class Ping {
    @GET
    public Response ping(){
        return Response
                .ok("ping")
                .build();
    }
}