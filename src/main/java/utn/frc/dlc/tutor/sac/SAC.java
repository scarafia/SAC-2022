package utn.frc.dlc.tutor.sac;

import utn.frc.dlc.tutor.sac.lib.db.DBManager;

public abstract class SAC {

    // PostgreSQL
    public static final String PG_URL = "jdbc:postgresql://localhost:5432/sacdb";
    public static final String PG_USR = "sacusr";
    public static final String PG_PWD = "sacpwd";
    private static final String PG_RES = "jdbc/SACDBPgRes";

    // H2 Database Engine
    public static final String H2_URL = "jdbc:h2:~/dbdata/dlc2022/sacdb";
    private static final String H2_RES = "jdbc/SACDBH2Res";

    // MySQL
    public static final String MY_URL = "jdbc:mysql://localhost:3306/sacdb?zeroDateTimeBehavior=CONVERT_TO_NULL";
    public static final String MY_USR = "sacusr";
    public static final String MY_PWD = "sacpwd";
    private static final String MY_RES = "jdbc/SACDBMyRes";

    public enum DBVendor {
        PostgreSQL,
        MySQL,
        H2DatabaseEngine,
        MSSqlServer,
        Default
    }

    public static DBManager getDB(DBVendor vendor, DBManager.DBConnectionMode mode) throws Exception {
        DBManager db = new DBManager();

        db.setConnectionMode(mode);

        switch (vendor) {
            case MySQL:
                db.setDriverName(DBManager.MYSQL_DRIVER_NAME);
                if (mode == DBManager.DBConnectionMode.SINGLE_CONNECTION_MODE) {
                    db.setUrl(MY_URL);
                    db.setUserName(MY_USR);
                    db.setPassword(MY_PWD);
                } else {
                    db.setResourceName(MY_RES);
                }
                break;
            case H2DatabaseEngine:
                db.setDriverName(DBManager.H2_DRIVER_NAME);
                if (mode == DBManager.DBConnectionMode.SINGLE_CONNECTION_MODE) {
                    db.setUrl(H2_URL);
                } else {
                    db.setResourceName(H2_RES);
                }
                break;
            default:
                db.setDriverName(DBManager.POSTGRES_DRIVER_NAME);
                if (mode == DBManager.DBConnectionMode.SINGLE_CONNECTION_MODE) {
                    db.setUrl(PG_URL);
                    db.setUserName(PG_USR);
                    db.setPassword(PG_PWD);
                } else {
                    db.setResourceName(PG_RES);
                }
        }

        db.connect();

        return db;
    }

    public static DBManager getDB(DBManager.DBConnectionMode mode) throws Exception {
        return getDB(DBVendor.PostgreSQL, mode);
    }

    public static DBManager getDB() throws Exception {
        return getDB(DBManager.DBConnectionMode.SINGLE_CONNECTION_MODE);
    }

}
