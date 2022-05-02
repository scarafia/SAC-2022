package utn.frc.dlc.tutor.sac.web.api;

import utn.frc.dlc.tutor.sac.lib.db.DBManager;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class DBManagerProducer {

    @Produces
    @RequestScoped
    public DBManager create() {
        DBManager db = null;

        try {
            db = new DBManager();
            db.setConnectionMode(DBManager.DBConnectionMode.SINGLE_CONNECTION_MODE);
            db.setDriverName(DBManager.POSTGRES_DRIVER_NAME);
            db.setUrl("jdbc:postgresql://localhost:5432/sacdb");
            db.setUserName("sacusr");
            db.setPassword("sacpwd");

            db.connect();
        } catch (Exception e) {
            // todo: log
        }

        return db;
    }

    public void destroy(@Disposes DBManager db) {
        if (db != null) db.close();
    }

}
