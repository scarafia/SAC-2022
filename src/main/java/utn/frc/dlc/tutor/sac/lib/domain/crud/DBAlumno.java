/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.lib.domain.crud;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utn.frc.dlc.tutor.sac.lib.db.DBManager;
import utn.frc.dlc.tutor.sac.lib.domain.Alumno;

/**
 *
 * @author scarafia
 */
public abstract class DBAlumno {

  public static final String ALUMNO_ID = "pid";
  public static final String ALUMNO_LEGAJO = "legajo";

  // ---------------------------------------------------------------------------
  // build
  // ---------------------------------------------------------------------------
  /**
   * Construye un Alumno a partir de un ResultSet previamente ejecutado.
   *
   * @param rs
   * @return
   * @throws Exception
   */
  protected static Alumno buildAlumno(ResultSet rs) throws Exception {
    Alumno alumno = null;
    if (rs.next()) {
      alumno = new Alumno(
        rs.getInt(DBPersona.PERSONA_ID),
        rs.getString(DBPersona.PERSONA_DNI),
        rs.getString(DBPersona.PERSONA_APELLIDO),
        rs.getString(DBPersona.PERSONA_NOMBRE),
        rs.getString(ALUMNO_LEGAJO)
      );
    }
    return alumno;
  }

  // ---------------------------------------------------------------------------
  protected static List<Alumno> buildAlumnos(ResultSet rs) throws Exception {
    List<Alumno> r = new ArrayList();

    Alumno alumno;
    while ((alumno = buildAlumno(rs)) != null) {
      r.add(alumno);
    }

    return r;
  }

  // ---------------------------------------------------------------------------
  // load
  // ---------------------------------------------------------------------------
  /**
   * Carga un alumno de la ddbb a partir de su identificador.
   *
   * @param db
   * @param id
   * @return
   * @throws Exception
   */
  public static Alumno loadDB(DBManager db, Integer id) throws Exception {
    if (db == null) {
      throw new Exception("DBAlumno Error: DBManager NO especificado");
    }
    if (id == null) {
      throw new Exception("DBAlumno Error: Identificador NO especificado");
    }

    String query = String.format(
      "SELECT * "
      + "FROM v_alumno "
      + "WHERE %s = ?",
      DBPersona.PERSONA_ID
    );

    db.prepareQuery(query);
    db.setInt(1, id);

    ResultSet rs = db.executeQuery();
    Alumno alumno = buildAlumno(rs);
    rs.close();
    return alumno;
  }

  // ---------------------------------------------------------------------------
  /**
   * Carga un conjunto de alumnos de la ddbb a partir de sus identificadores.
   *
   * @param db
   * @param ids
   * @return
   * @throws Exception
   */
  public static List<Alumno> loadDB(DBManager db, Integer[] ids) throws Exception {
    if (ids == null) {
      throw new Exception("DBAlumno Error: Identificadores NO especificados");
    }

    return loadList(db, ids);
  }

  // ---------------------------------------------------------------------------
  /**
   * Carga un alumno de la ddbb a partir de su legajo.
   *
   * @param db
   * @param legajo
   * @return
   * @throws Exception
   */
  public static Alumno loadDB(DBManager db, String legajo) throws Exception {
    if (db == null) {
      throw new Exception("DBAlumno Error: DBManager NO especificado");
    }
    if (legajo == null) {
      throw new Exception("DBAlumno Error: Legajo NO especificado");
    }

    String query = String.format(
      "SELECT * "
      + "FROM v_alumno "
      + "WHERE %s = ?",
      ALUMNO_LEGAJO
    );

    db.prepareQuery(query);
    db.setString(1, legajo);

    ResultSet rs = db.executeQuery();
    Alumno alumno = buildAlumno(rs);
    rs.close();
    return alumno;
  }

  // ---------------------------------------------------------------------------
  // save
  // ---------------------------------------------------------------------------
  /**
   * Guarda un alumno en la ddbb.
   *
   * @param db
   * @param alumno
   * @return
   * @throws Exception
   */
  public static Integer saveDB(DBManager db, Alumno alumno) throws Exception {
    if (db == null) {
      throw new Exception("DBAlumno Error: DBManager NO especificado");
    }
    if (alumno == null) {
      throw new Exception("DBAlumno Error: Alumno NO especificado");
    }

    if (db.getDriverName().equals(DBManager.POSTGRES_DRIVER_NAME)) {
      return pgSaveDB(db, alumno);
    }

    Boolean isNew = alumno.getId() == null;

    Integer id = DBPersona.saveDB(db, alumno);

    if (isNew) {
      insertDB(db, id, alumno.getLegajo());
    } else {
      updateDB(db, id, alumno.getLegajo());
    }

    return id;
  }

    // ---------------------------------------------------------------------------
  public static void saveDB(DBManager db, Integer id, String legajo) throws Exception {
    if (db == null) {
      throw new Exception("DBAlumno Error: DBManager NO especificado");
    }
    if (id == null) {
      throw new Exception("DBAlumno Error: Identificador NO especificado");
    }
    if (legajo == null || legajo.isEmpty()) {
      throw new Exception("DBAlumno Error: Legajo NO especificado");
    }

    if (db.getDriverName().equals(DBManager.POSTGRES_DRIVER_NAME)) {
      pgSaveDB(db, id, legajo);
      return;
    }
    
    ResultSet rs = db.executeQuery(String.format(
      "SELECT COUNT(*) FROM alumno WHERE %s = %d", ALUMNO_ID, id
    ));
    rs.next();
    
    boolean isNew = rs.getInt(1) == 0;
    
    if (isNew) {
      insertDB(db, id, legajo);
    } else {
      updateDB(db, id, legajo);
    }
  }

// ---------------------------------------------------------------------------
  private static void insertDB(DBManager db, Integer id, String legajo) throws Exception {
    db.prepareUpdate(String.format(
      "INSERT INTO alumno (%s, %s) "
      + "VALUES (?, ?)",
      ALUMNO_ID, ALUMNO_LEGAJO
    ));
    db.setInt(1, id);
    db.setString(2, legajo);
    db.executeUpdate();
  }

  // ---------------------------------------------------------------------------
  private static void updateDB(DBManager db, Integer id, String legajo) throws Exception {
    db.prepareUpdate(String.format(
      "UPDATE alumno SET "
      + "%s = ? "
      + "WHERE %s = ?",
      ALUMNO_LEGAJO,
      ALUMNO_ID
    ));
    db.setString(1, legajo);
    db.setInt(2, id);
    db.executeUpdate();
  }

  // ---------------------------------------------------------------------------
  private static Integer pgSaveDB(DBManager db, Alumno alumno) throws Exception {

    String query = "SELECT fn_savealumno(?, ?, ?, ?, ?)";

    db.prepareQuery(query);
    db.setInt(1, alumno.getId());
    db.setString(2, alumno.getDni());
    db.setString(3, alumno.getApellido());
    db.setString(4, alumno.getNombre());
    db.setString(5, alumno.getLegajo());

    Integer id = null;
    ResultSet rs = db.executeQuery();
    if (rs.first()) {
      id = rs.getInt(1);
    }
    rs.close();

    alumno.setId(id);

    return id;
  }

  // ---------------------------------------------------------------------------
  private static void pgSaveDB(DBManager db, Integer id, String legajo) throws Exception {

    String query = "SELECT pr_savealumno(?, ?)";

    db.prepareQuery(query);
    db.setInt(1, id);
    db.setString(2, legajo);

    db.executeQuery().close();
  }

  // ---------------------------------------------------------------------------
  // delete
  // ---------------------------------------------------------------------------
  /**
   * Elimina un alumno de la ddbb a partir de su identificador.
   *
   * @param db
   * @param id
   * @return
   * @throws Exception
   */
  public static Alumno deleteDB(DBManager db, Integer id) throws Exception {
    Alumno alumno = loadDB(db, id);

    if (alumno != null) {
      db.prepareUpdate(String.format(
        "DELETE FROM alumno WHERE %s = ?", ALUMNO_ID
      ));
      db.setInt(1, id);
      db.execute();
    }

    return alumno;

  }

  // ---------------------------------------------------------------------------
  /**
   * Elimina un alumno de la ddbb.
   *
   * @param db
   * @param alumno
   * @return
   * @throws Exception
   */
  public static Alumno deleteDB(DBManager db, Alumno alumno) throws Exception {
    if (alumno == null) {
      throw new Exception("DBAlumno Error: Alumno NO especificado");
    }

    return deleteDB(db, alumno.getId());
  }

  // ---------------------------------------------------------------------------
  /**
   * Elimina un conjunto de alumnos de la ddbb a partir de sus identificadores.
   *
   * @param db
   * @param ids
   * @return
   * @throws Exception
   */
  public static List<Alumno> deleteDB(DBManager db, Integer[] ids) throws Exception {
    List<Alumno> alumnos = loadList(db, ids);

    if (alumnos != null) {
      db.prepareUpdate(String.format(
        "DELETE FROM alumno WHERE %s IN (?)", ALUMNO_ID
      ));
      db.setArray(1, "INTEGER", ids);
      db.execute();
    }

    return alumnos;
  }

  // ---------------------------------------------------------------------------
  /**
   * Elimina un conjunto de alumnos de la ddbb.
   *
   * @param db
   * @param alumnos
   * @return
   * @throws Exception
   */
  public static List<Alumno> deleteDB(DBManager db, Alumno[] alumnos) throws Exception {
    int l = alumnos.length;
    Integer[] ids = new Integer[l];

    for (int i = 0; i < l; i++) {
      ids[i] = alumnos[i].getId();
    }

    return deleteDB(db, ids);
  }

  // ---------------------------------------------------------------------------
  // list
  // ---------------------------------------------------------------------------
  /**
   * Genera una lista de alumnos.
   *
   * @param db
   * @param ids
   * @param limit
   * @param offset
   * @return
   * @throws Exception
   */
  public static List<Alumno> loadList(DBManager db, Integer[] ids, int limit, int offset) throws Exception {
    if (db == null) {
      throw new Exception("DBAlumno Error: DBManager NO especificado");
    }
    if (limit < 0) {
      throw new Exception("DBAlumno Error: limit incorrecto");
    }
    if (offset < 0) {
      throw new Exception("DBAlumno Error: offset incorrecto");
    }

    String query = DBManager.buildSelectQuery(
      "*",
      "v_alumno",
      ids == null ? null : String.format("%s IN (?)", DBPersona.PERSONA_ID),
      null, null,
      String.format("%s, %s", DBPersona.PERSONA_APELLIDO, DBPersona.PERSONA_NOMBRE),
      limit, offset
    );

    db.prepareQuery(query);

    int parameterIndex = 1;
    if (ids != null) {
      db.setArray(parameterIndex++, "INTEGER", ids);
    }

    if (limit > 0) {
      db.setInt(parameterIndex++, limit);
    }

    if (offset > 0) {
      db.setInt(parameterIndex++, offset);
    }

    ResultSet rs = db.executeQuery(query);
    List<Alumno> alumnos = buildAlumnos(rs);
    rs.close();

    return alumnos;
  }

  // ---------------------------------------------------------------------------
  public static List<Alumno> loadList(DBManager db, Integer[] ids) throws Exception {
    return loadList(db, ids, 0, 0);
  }

  // ---------------------------------------------------------------------------
  public static List<Alumno> loadList(DBManager db, int limit, int offset) throws Exception {
    return loadList(db, null, limit, offset);
  }

  // ---------------------------------------------------------------------------
  public static List<Alumno> loadList(DBManager db) throws Exception {
    return loadList(db, null);
  }

}
