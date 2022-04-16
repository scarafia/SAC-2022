package utn.frc.dlc.tutor.sac.web.api.resources;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ping-client")
public class PingClient {

    @GET
    public Response ping(){
        Client client = ClientBuilder.newClient();

        String r = client
                .target("http://localhost:8080/api/ping")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class);

        return Response
                .ok(r)
                .build();
    }

}