package utn.frc.dlc.tutor.sac.web.api.cdi;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/cdi")
public class CDITestAPI {

    @Inject
    private CDITestClass test;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CDITestClass ping() {
        return test;
    }
}