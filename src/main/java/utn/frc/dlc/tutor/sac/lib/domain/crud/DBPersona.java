/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.lib.domain.crud;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utn.frc.dlc.tutor.sac.lib.db.DBManager;
import utn.frc.dlc.tutor.sac.lib.domain.Persona;

/**
 *
 * @author scarafia
 */
public abstract class DBPersona {

  public static final String PERSONA_ID = "pid";
  public static final String PERSONA_DNI = "dni";
  public static final String PERSONA_APELLIDO = "apellido";
  public static final String PERSONA_NOMBRE = "nombre";
  
  // ---------------------------------------------------------------------------
  // next ==> legacy code ==> deprecated ==> do not use
  // ---------------------------------------------------------------------------
  /**
   * Obtiene el siguiente identificador de persona.
   * 
   * @deprecated
   *   Por cuestiones de unicidad se decide eliminar el uso de secuencias.
   *   Se deja este snipet sólo para esta clase por motivos testimoniales.
   *
   * @param db
   * @return
   * @throws UnsupportedOperationException, Exception
   */
  @Deprecated
  public static int getNextId(DBManager db) throws UnsupportedOperationException, Exception {
    
    if (true) throw new UnsupportedOperationException(
      "No implementado!"
    );

    if (db == null) {
      throw new Exception("DBPersona Error: DBManager NO especificado");
    }

    if (!db.getDriverName().equals(DBManager.POSTGRES_DRIVER_NAME)) {
      throw new UnsupportedOperationException("Not implemented yet");
    }

    ResultSet rs = db.executeQuery("SELECT fn_getidpersona() AS id");

    if (!rs.first()) {
      throw new Exception("DBPersona Error: No se pudo obtener identificador");
    }

    int id = rs.getInt("id");

    return id;
  }

  // ---------------------------------------------------------------------------
  // build
  // ---------------------------------------------------------------------------
  /**
   * Construye una Persona a partir de un ResultSet previamente ejecutado.
   *
   * @param rs
   * @return
   * @throws Exception
   */
  protected static Persona buildPersona(ResultSet rs) throws Exception {
    Persona persona = null;
    if (rs.next()) {
      persona = new Persona(
        rs.getInt(PERSONA_ID),
        rs.getString(PERSONA_DNI),
        rs.getString(PERSONA_APELLIDO),
        rs.getString(PERSONA_NOMBRE)
      );
    }
    return persona;
  }

  // ---------------------------------------------------------------------------
  private static List<Persona> buildPersonas(ResultSet rs) throws Exception {
    List<Persona> r = new ArrayList();

    Persona persona;
    while ((persona = buildPersona(rs)) != null) {
      r.add(persona);
    }

    return r;
  }

  // ---------------------------------------------------------------------------
  // load
  // ---------------------------------------------------------------------------
  /**
   * Carga una persona de la ddbb a partir de su identificador.
   *
   * @param db
   * @param id
   * @return
   * @throws Exception
   */
  public static Persona loadDB(DBManager db, Integer id) throws Exception {
    if (db == null) {
      throw new Exception("DBPersona Error: DBManager NO especificado");
    }
    if (id == null) {
      throw new Exception("DBPersona Error: Identificador NO especificado");
    }

    String query = String.format(
      "SELECT * FROM persona p WHERE p.%s = ?", PERSONA_ID
    );

    db.prepareQuery(query);
    db.setInt(1, id);

    ResultSet rs = db.executeQuery();
    Persona persona = buildPersona(rs);
    rs.close();
    return persona;
  }

  // ---------------------------------------------------------------------------
  /**
   * Carga un conjunto de personas de la ddbb a partir de sus identificadores.
   *
   * @param db
   * @param ids
   * @return
   * @throws Exception
   */
  public static List<Persona> loadDB(DBManager db, Integer[] ids) throws Exception {
    if (ids == null) {
      throw new Exception("DBPersona Error: Identificadores NO especificados");
    }

    return loadList(db, ids);
  }

  // ---------------------------------------------------------------------------
  /**
   * Carga una persona de la ddbb a partir de su dni.
   *
   * @param db
   * @param dni
   * @return
   * @throws Exception
   */
  public static Persona loadDB(DBManager db, String dni) throws Exception {
    if (db == null) {
      throw new Exception("DBPersona Error: DBManager NO especificado");
    }
    if (dni == null) {
      throw new Exception("DBPersona Error: DNI NO especificado");
    }

    String query = String.format(
      "SELECT * FROM persona p WHERE p.%s = ?", PERSONA_DNI
    );

    db.prepareQuery(query);
    db.setString(1, dni);

    ResultSet rs = db.executeQuery();
    Persona persona = buildPersona(rs);
    rs.close();
    return persona;
  }

  // ---------------------------------------------------------------------------
  // save
  // ---------------------------------------------------------------------------
  /**
   * Guarda una persona en la ddbb. Obsérvese que la función invoca una
   * instrucción sin precompilarla y sin ser debidamente parseada, lo cual trae
   * 2 incovenientes: 1. Posible sql-injection 2. Posible error por caracteres
   * especiales
   *
   * @param db
   * @param persona
   * @throws Exception
   */
  public static Integer saveDBError(DBManager db, Persona persona) throws Exception {
    if (db == null) {
      throw new Exception("DBPersona ERROR: DBManager NO especificado");
    }
    if (persona == null) {
      throw new Exception("DBPersona ERROR: Persona NO especificada");
    }

    String statement = persona.getId() == null
      ? String.format(
        "INSERT INTO persona (%s, %s, %s) "
        + "VALUES ('%s', '%s', '%s')",
        PERSONA_DNI, PERSONA_APELLIDO, PERSONA_NOMBRE,
        persona.getDni(),
        persona.getApellido(),
        persona.getNombre()
      )
      : String.format("UPDATE persona SET "
        + "%s = '%s', "
        + "%s = '%s', "
        + "%s = '%s' "
        + "WHERE %s = %d",
        PERSONA_DNI, persona.getDni(),
        PERSONA_APELLIDO, persona.getApellido(),
        PERSONA_NOMBRE, persona.getNombre(),
        PERSONA_ID, persona.getId()
      );

    Integer id = null;

    ResultSet rs = db.executeUpdate(statement).getGeneratedKeys();
    if (rs.next()) {
      id = rs.getInt(1);
    }
    rs.close();

    return id;
  }

  // ---------------------------------------------------------------------------
  /**
   * Guarda una persona en la ddbb.
   *
   * @param db
   * @param persona
   * @return
   * @throws Exception
   */
  public static Integer saveDB(DBManager db, Persona persona) throws Exception {
    if (db == null) {
      throw new Exception("DBPersona Error: DBManager NO especificado");
    }
    if (persona == null) {
      throw new Exception("DBPersona Error: Persona NO especificada");
    }
    
    if (db.getDriverName().equals(DBManager.POSTGRES_DRIVER_NAME)) {
      return pgSaveDB(db, persona);
    }

    return persona.getId() == null
      ? insertDB(db, persona)
      : updateDB(db, persona);
  }

  // ---------------------------------------------------------------------------
  private static Integer insertDB(DBManager db, Persona persona) throws Exception {

    db.prepareUpdate(String.format(
      "INSERT INTO persona (%s, %s, %s) "
      + "VALUES (?, ?, ?)",
      PERSONA_DNI, PERSONA_APELLIDO, PERSONA_NOMBRE
    ));
    db.setString(1, persona.getDni());
    db.setString(2, persona.getApellido());
    db.setString(3, persona.getNombre());

    Integer id = null;
    ResultSet rs = db.executeUpdate().getGeneratedKeys();
    if (rs.first()) {
      id = rs.getInt(1);
    }
    rs.close();

    return id;
  }

  // ---------------------------------------------------------------------------
  private static Integer updateDB(DBManager db, Persona persona) throws Exception {

    db.prepareUpdate(String.format("UPDATE persona SET "
      + "%s = ?, %s = ?, %s = ? "
      + "WHERE %s = ?",
      PERSONA_DNI, PERSONA_APELLIDO, PERSONA_NOMBRE,
      PERSONA_ID
    ));
    db.setString(1, persona.getDni());
    db.setString(2, persona.getApellido());
    db.setString(3, persona.getNombre());
    db.setInt(4, persona.getId());
    db.executeUpdate();

    return persona.getId();
  }

  // ---------------------------------------------------------------------------
  private static Integer pgSaveDB(DBManager db, Persona persona) throws Exception {
    System.out.println("IS PG");

    String query = "SELECT fn_savepersona(?, ?, ?, ?)";

    db.prepareQuery(query);
    db.setInt(1, persona.getId());
    db.setString(2, persona.getDni());
    db.setString(3, persona.getApellido());
    db.setString(4, persona.getNombre());

    Integer id = null;
    ResultSet rs = db.executeQuery();
    if (rs.first()) {
      id = rs.getInt(1);
    }
    rs.close();

    persona.setId(id);

    return id;
  }

  // ---------------------------------------------------------------------------
  // delete
  // ---------------------------------------------------------------------------
  /**
   * Elimina una persona de la ddbb a partir de su identificador.
   *
   * @param db
   * @param id
   * @return
   * @throws Exception
   */
  public static Persona deleteDB(DBManager db, Integer id) throws Exception {
    Persona persona = loadDB(db, id);

    if (persona != null) {
      db.prepareUpdate(String.format("DELETE FROM persona WHERE %s = ?", PERSONA_ID));
      db.setInt(1, id);
      db.execute();
    }

    return persona;
  }

  // ---------------------------------------------------------------------------
  /**
   * Elimina una persona de la ddbb.
   *
   * @param db
   * @param persona
   * @return
   * @throws Exception
   */
  public static Persona deleteDB(DBManager db, Persona persona) throws Exception {
    if (persona == null) {
      throw new Exception("DBPersona Error: Persona NO especificada");
    }

    return deleteDB(db, persona.getId());
  }

  // ---------------------------------------------------------------------------
  /**
   * Elimina un conjunto de personas de la ddbb a partir de sus identificadores.
   *
   * @param db
   * @param ids
   * @return
   * @throws Exception
   */
  public static List<Persona> deleteDB(DBManager db, Integer[] ids) throws Exception {
    List<Persona> personas = loadList(db, ids);

    if (personas != null) {
      db.prepareUpdate(String.format("DELETE FROM persona WHERE %s IN (?)", PERSONA_ID));
      db.setArray(1, "INTEGER", ids);
      db.execute();
    }

    return personas;
  }

  // ---------------------------------------------------------------------------
  /**
   * Elimina un conjunto de personas de la ddbb.
   *
   * @param db
   * @param personas
   * @return
   * @throws Exception
   */
  public static List<Persona> deleteDB(DBManager db, Persona[] personas) throws Exception {
    int l = personas.length;
    Integer[] ids = new Integer[l];

    for (int i = 0; i < l; i++) {
      ids[i] = personas[i].getId();
    }

    return deleteDB(db, ids);
  }

  // ---------------------------------------------------------------------------
  // list
  // ---------------------------------------------------------------------------
  /**
   * Genera una lista de personas.
   *
   * @param db
   * @param ids
   * @param limit
   * @param offset
   * @return
   * @throws Exception
   */
  private static List<Persona> loadList(DBManager db, Integer[] ids, String order, int limit, int offset) throws Exception {
    if (db == null) {
      throw new Exception("DBPersona Error: DBManager NO especificado");
    }
    if (limit < 0) {
      throw new Exception("DBPersona Error: limit incorrecto");
    }
    if (offset < 0) {
      throw new Exception("DBPersona Error: offset incorrecto");
    }

    String query = DBManager.buildSelectQuery(
      "*",
      "persona p",
      ids == null ? null : String.format("p.%s IN (?)", PERSONA_ID),
      null, null,
      order,
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

    ResultSet rs = db.executeQuery();
    List<Persona> personas = buildPersonas(rs);
    rs.close();

    return personas;
  }
  
  // ---------------------------------------------------------------------------
  // default
  // ---------------------------------------------------------------------------
  public static List<Persona> loadList(DBManager db, Integer[] ids, int limit, int offset) throws Exception {
    return loadList(db, ids, null, limit, offset);
  }
  
  // ---------------------------------------------------------------------------
  public static List<Persona> loadList(DBManager db, Integer[] ids) throws Exception {
    return loadList(db, ids, 0, 0);
  }

  // ---------------------------------------------------------------------------
  public static List<Persona> loadList(DBManager db, int limit, int offset) throws Exception {
    return loadList(db, null, limit, offset);
  }

  // ---------------------------------------------------------------------------
  public static List<Persona> loadList(DBManager db) throws Exception {
    return loadList(db, null);
  }
  
  // ---------------------------------------------------------------------------
  // by Id
  // ---------------------------------------------------------------------------
  public static List<Persona> loadListById(DBManager db, Integer[] ids, int limit, int offset) throws Exception {
    return loadList(db, ids, String.format("p.%s", PERSONA_ID), limit, offset);
  }
  
  // ---------------------------------------------------------------------------
  public static List<Persona> loadListById(DBManager db, Integer[] ids) throws Exception {
    return loadListById(db, ids, 0, 0);
  }

  // ---------------------------------------------------------------------------
  public static List<Persona> loadListById(DBManager db, int limit, int offset) throws Exception {
    return loadListById(db, null, limit, offset);
  }

  // ---------------------------------------------------------------------------
  public static List<Persona> loadListById(DBManager db) throws Exception {
    return loadListById(db, null);
  }
  
  // ---------------------------------------------------------------------------
  // by DNI
  // ---------------------------------------------------------------------------
  public static List<Persona> loadListByDNI(DBManager db, Integer[] ids, int limit, int offset) throws Exception {
    return loadList(db, ids, String.format("p.%s", PERSONA_DNI), limit, offset);
  }
  
  // ---------------------------------------------------------------------------
  public static List<Persona> loadListByDNI(DBManager db, Integer[] ids) throws Exception {
    return loadListByDNI(db, ids, 0, 0);
  }

  // ---------------------------------------------------------------------------
  public static List<Persona> loadListByDNI(DBManager db, int limit, int offset) throws Exception {
    return loadListByDNI(db, null, limit, offset);
  }

  // ---------------------------------------------------------------------------
  public static List<Persona> loadListByDNI(DBManager db) throws Exception {
    return loadListByDNI(db, null);
  }
  
  // ---------------------------------------------------------------------------
  // by ApeNom
  // ---------------------------------------------------------------------------
  public static List<Persona> loadListByApeNom(DBManager db, Integer[] ids, int limit, int offset) throws Exception {
    return loadList(db, ids, String.format("p.%s, p.%s", PERSONA_APELLIDO, PERSONA_NOMBRE), limit, offset);
  }
  
  // ---------------------------------------------------------------------------
  public static List<Persona> loadListByApeNom(DBManager db, Integer[] ids) throws Exception {
    return loadListByApeNom(db, ids, 0, 0);
  }

  // ---------------------------------------------------------------------------
  public static List<Persona> loadListByApeNom(DBManager db, int limit, int offset) throws Exception {
    return loadListByApeNom(db, null, limit, offset);
  }

  // ---------------------------------------------------------------------------
  public static List<Persona> loadListByApeNom(DBManager db) throws Exception {
    return loadListByApeNom(db, null);
  }
  
}
