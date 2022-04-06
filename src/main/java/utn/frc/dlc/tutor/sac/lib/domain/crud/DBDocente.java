/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.lib.domain.crud;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utn.frc.dlc.tutor.sac.lib.db.DBManager;
import utn.frc.dlc.tutor.sac.lib.domain.Docente;

/**
 *
 * @author scarafia
 */
public abstract class DBDocente {

  public static final String DOCENTE_ID = "pid";
  public static final String DOCENTE_LEGAJO = "legajo";

  // ---------------------------------------------------------------------------
  // build
  // ---------------------------------------------------------------------------
  /**
   * Construye un Docente a partir de un ResultSet previamente ejecutado.
   *
   * @param rs
   * @return
   * @throws Exception
   */
  protected static Docente buildDocente(ResultSet rs) throws Exception {
    Docente docente = null;
    if (rs.next()) {
      docente = new Docente(
        rs.getInt(DBPersona.PERSONA_ID),
        rs.getString(DBPersona.PERSONA_DNI),
        rs.getString(DBPersona.PERSONA_APELLIDO),
        rs.getString(DBPersona.PERSONA_NOMBRE),
        rs.getString(DOCENTE_LEGAJO)
      );
    }
    return docente;
  }

  // ---------------------------------------------------------------------------
  protected static List<Docente> buildDocentes(ResultSet rs) throws Exception {
    List<Docente> r = new ArrayList();

    Docente docente;
    while ((docente = buildDocente(rs)) != null) {
      r.add(docente);
    }

    return r;
  }

  // ---------------------------------------------------------------------------
  // load
  // ---------------------------------------------------------------------------
  /**
   * Carga un docente de la ddbb a partir de su identificador.
   *
   * @param db
   * @param id
   * @return
   * @throws Exception
   */
  public static Docente loadDB(DBManager db, Integer id) throws Exception {
    if (db == null) {
      throw new Exception("DBDocente Error: DBManager NO especificado");
    }
    if (id == null) {
      throw new Exception("DBDocente Error: Identificador NO especificado");
    }

    String query = String.format(
      "SELECT * "
      + "FROM v_docente "
      + "WHERE %s = ?",
      DBPersona.PERSONA_ID
    );

    db.prepareQuery(query);
    db.setInt(1, id);

    ResultSet rs = db.executeQuery();
    Docente docente = buildDocente(rs);
    rs.close();
    return docente;
  }

  // ---------------------------------------------------------------------------
  /**
   * Carga un conjunto de docentes de la ddbb a partir de sus identificadores.
   *
   * @param db
   * @param ids
   * @return
   * @throws Exception
   */
  public static List<Docente> loadDB(DBManager db, Integer[] ids) throws Exception {
    if (ids == null) {
      throw new Exception("DBDocente Error: Identificadores NO especificados");
    }

    return loadList(db, ids);
  }

  // ---------------------------------------------------------------------------
  /**
   * Carga un docente de la ddbb a partir de su legajo.
   *
   * @param db
   * @param legajo
   * @return
   * @throws Exception
   */
  public static Docente loadDB(DBManager db, String legajo) throws Exception {
    if (db == null) {
      throw new Exception("DBDocente Error: DBManager NO especificado");
    }
    if (legajo == null) {
      throw new Exception("DBDocente Error: Legajo NO especificado");
    }

    String query = String.format(
      "SELECT p.%s, p.%s, p.%s, p.%s, a.%s "
      + "FROM persona p "
      + "JOIN docente a ON p.%s = a.%s "
      + "WHERE a.%s = ?",
      DBPersona.PERSONA_ID, DBPersona.PERSONA_DNI, DBPersona.PERSONA_APELLIDO, DBPersona.PERSONA_NOMBRE,
      DOCENTE_LEGAJO,
      DBPersona.PERSONA_ID, DOCENTE_ID,
      DOCENTE_LEGAJO
    );

    db.prepareQuery(query);
    db.setString(1, legajo);

    ResultSet rs = db.executeQuery();
    Docente docente = buildDocente(rs);
    rs.close();
    return docente;
  }

  // ---------------------------------------------------------------------------
  // save
  // ---------------------------------------------------------------------------
  /**
   * Guarda un docente en la ddbb.
   *
   * @param db
   * @param docente
   * @return
   * @throws Exception
   */
  public static Integer saveDB(DBManager db, Docente docente) throws Exception {
    if (db == null) {
      throw new Exception("DBDocente Error: DBManager NO especificado");
    }
    if (docente == null) {
      throw new Exception("DBDocente Error: Docente NO especificado");
    }

    if (db.getDriverName().equals(DBManager.POSTGRES_DRIVER_NAME)) {
      return pgSaveDB(db, docente);
    }

    boolean isNew = docente.getId() == null;

    Integer id = DBPersona.saveDB(db, docente);
    
    if (isNew) {
      insertDB(db, id, docente.getLegajo());
    } else {
      updateDB(db, id, docente.getLegajo());
    }

    return id;
  }

  // ---------------------------------------------------------------------------
  public static void saveDB(DBManager db, Integer id, String legajo) throws Exception {
    if (db == null) {
      throw new Exception("DBDocente Error: DBManager NO especificado");
    }
    if (id == null) {
      throw new Exception("DBDocente Error: Identificador NO especificado");
    }
    if (legajo == null || legajo.isEmpty()) {
      throw new Exception("DBDocente Error: Legajo NO especificado");
    }

    if (db.getDriverName().equals(DBManager.POSTGRES_DRIVER_NAME)) {
      pgSaveDB(db, id, legajo);
      return;
    }
    
    ResultSet rs = db.executeQuery(String.format(
      "SELECT COUNT(*) FROM docente WHERE %s = %d", DOCENTE_ID, id
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
      "INSERT INTO docente (%s, %s) "
      + "VALUES (?, ?)",
      DOCENTE_ID, DOCENTE_LEGAJO
    ));
    db.setInt(1, id);
    db.setString(2, legajo);
    db.executeUpdate();
  }

  // ---------------------------------------------------------------------------
  private static void updateDB(DBManager db, Integer id, String legajo) throws Exception {
    db.prepareUpdate(String.format(
      "UPDATE docente SET "
      + "%s = ? "
      + "WHERE %s = ?",
      DOCENTE_LEGAJO,
      DOCENTE_ID
    ));
    db.setString(1, legajo);
    db.setInt(2, id);
    db.executeUpdate();
  }

  // ---------------------------------------------------------------------------
  private static Integer pgSaveDB(DBManager db, Docente docente) throws Exception {

    String query = "SELECT fn_savedocente(?, ?, ?, ?, ?)";

    db.prepareQuery(query);
    db.setInt(1, docente.getId());
    db.setString(2, docente.getDni());
    db.setString(3, docente.getApellido());
    db.setString(4, docente.getNombre());
    db.setString(5, docente.getLegajo());

    Integer id = null;
    ResultSet rs = db.executeQuery();
    if (rs.first()) {
      id = rs.getInt(1);
    }
    rs.close();

    docente.setId(id);

    return id;
  }

  // ---------------------------------------------------------------------------
  private static void pgSaveDB(DBManager db, Integer id, String legajo) throws Exception {

    String query = "SELECT pr_savedocente(?, ?)";

    db.prepareQuery(query);
    db.setInt(1, id);
    db.setString(2, legajo);

    db.executeQuery().close();
  }

  // ---------------------------------------------------------------------------
  // delete
  // ---------------------------------------------------------------------------
  /**
   * Elimina un docente de la ddbb a partir de su identificador.
   *
   * @param db
   * @param id
   * @return
   * @throws Exception
   */
  public static Docente deleteDB(DBManager db, Integer id) throws Exception {
    Docente docente = loadDB(db, id);

    if (docente != null) {
      db.prepareUpdate(String.format(
        "DELETE FROM docente WHERE %s = ?", DOCENTE_ID
      ));
      db.setInt(1, id);
      db.execute();
    }

    return docente;
  }

  // ---------------------------------------------------------------------------
  /**
   * Elimina un docente de la ddbb.
   *
   * @param db
   * @param docente
   * @return
   * @throws Exception
   */
  public static Docente deleteDB(DBManager db, Docente docente) throws Exception {
    if (docente == null) {
      throw new Exception("DBDocente Error: Docente NO especificado");
    }

    return deleteDB(db, docente.getId());
  }

  // ---------------------------------------------------------------------------
  /**
   * Elimina un conjunto de docentes de la ddbb a partir de sus identificadores.
   *
   * @param db
   * @param ids
   * @return
   * @throws Exception
   */
  public static List<Docente> deleteDB(DBManager db, Integer[] ids) throws Exception {
    List<Docente> docentes = loadList(db, ids);

    if (docentes != null) {
      db.prepareUpdate(String.format(
        "DELETE FROM docente WHERE %s IN (?)", DOCENTE_ID
      ));
      db.setArray(1, "INTEGER", ids);
      db.execute();
    }

    return docentes;
  }

  // ---------------------------------------------------------------------------
  /**
   * Elimina un conjunto de docentes de la ddbb.
   *
   * @param db
   * @param docentes
   * @return
   * @throws Exception
   */
  public static List<Docente> deleteDB(DBManager db, Docente[] docentes) throws Exception {
    int l = docentes.length;
    Integer[] ids = new Integer[l];

    for (int i = 0; i < l; i++) {
      ids[i] = docentes[i].getId();
    }

    return deleteDB(db, ids);
  }

  // ---------------------------------------------------------------------------
  // list
  // ---------------------------------------------------------------------------
  /**
   * Genera una lista de docentes.
   *
   * @param db
   * @param ids
   * @param limit
   * @param offset
   * @return
   * @throws Exception
   */
  public static List<Docente> loadList(DBManager db, Integer[] ids, int limit, int offset) throws Exception {
    if (db == null) {
      throw new Exception("DBDocente Error: DBManager NO especificado");
    }
    if (limit < 0) {
      throw new Exception("DBDocente Error: limit incorrecto");
    }
    if (offset < 0) {
      throw new Exception("DBDocente Error: offset incorrecto");
    }

    String query = DBManager.buildSelectQuery(
      "*",
      "v_docente",
      ids == null ? null : String.format("%s IN (?)", DBPersona.PERSONA_ID),
      null, null,
      String.format("p.%s, p.%s", DBPersona.PERSONA_APELLIDO, DBPersona.PERSONA_NOMBRE),
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
    List<Docente> docentes = buildDocentes(rs);
    rs.close();

    return docentes;
  }

  // ---------------------------------------------------------------------------
  public static List<Docente> loadList(DBManager db, Integer[] ids) throws Exception {
    return loadList(db, ids, 0, 0);
  }

  // ---------------------------------------------------------------------------
  public static List<Docente> loadList(DBManager db, int limit, int offset) throws Exception {
    return loadList(db, null, limit, offset);
  }

  // ---------------------------------------------------------------------------
  public static List<Docente> loadList(DBManager db) throws Exception {
    return loadList(db, null);
  }

}
