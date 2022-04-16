package utn.frc.dlc.tutor.sac.web.api.personas;

import utn.frc.dlc.tutor.sac.SAC;
import utn.frc.dlc.tutor.sac.lib.db.DBManager;
import utn.frc.dlc.tutor.sac.lib.domain.Persona;
import utn.frc.dlc.tutor.sac.lib.domain.crud.DBPersona;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("personas")
public class PersonasAPI {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Persona> getPersonas() {

        List<Persona> personas = new ArrayList();
        DBManager db = null;

        try {
            db = SAC.getDB();
            personas = DBPersona.loadListById(db);
        } catch (Exception e) {
            // todo: log
        } finally {
            if (db != null) db.close();
        }

        return personas;
    }

    @GET
    @Path("with-error")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonasWithError() {

        List<Persona> personas = new ArrayList();
        DBManager db = null;
        Response r;

        try {
            db = SAC.getDB();
            personas = DBPersona.loadListById(db);
            r = Response
                    .ok(personas)
                    .build();

        } catch (Exception e) {
            // todo: log
            r = Response
                    .status(404)
                    .entity(e.getMessage())
                    .build();
        } finally {
            if (db != null) db.close();
        }

        return r;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Persona getPersona(@PathParam("id") Integer id) {
        Persona persona = null;
        DBManager db = null;

        try {
            db = SAC.getDB();
            persona = DBPersona.loadDB(db, id);
        } catch (Exception e) {
            // todo: log
        } finally {
            if (db != null) db.close();
        }

        return persona;
    }

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Persona postPersona(Persona persona) {
        Persona r = null;

        //todo: validar lo que haga falta.
        if (persona != null && persona.getId() == null) {
            r = savePersona(persona);
        }

        return r;
    }

    @PUT
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Persona putPersona(Persona persona) {
        Persona r = null;

        //todo: validar lo que haga falta.
        if (persona != null && persona.getId() != null) {
            r = savePersona(persona);
        }

        return r;
    }

    private Persona savePersona(Persona persona) {
        Persona r = null;
        DBManager db = null;

        try {
            db = SAC.getDB();
            Integer id = DBPersona.saveDB(db, persona);
            r = DBPersona.loadDB(db, id);
        } catch (Exception e) {
            // todo: log
            // r = new Persona(e.getMessage(), null, null);
        } finally {
            if (db != null) db.close();
        }

        return r;
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Persona deletePersona(@PathParam("id") int id) {
        Persona r = null;
        DBManager db = null;

        //todo: validar lo que haga falta.
        try {
            db = SAC.getDB();
            r = DBPersona.deleteDB(db, id);
        } catch (Exception e) {
            // todo: log
        } finally {
            if (db != null) db.close();
        }

        return r;
    }

}