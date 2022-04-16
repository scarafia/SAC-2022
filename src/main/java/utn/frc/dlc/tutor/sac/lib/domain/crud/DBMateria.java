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
import utn.frc.dlc.tutor.sac.lib.domain.Materia;

/**
 *
 * @author scarafia
 */
public abstract class DBMateria {

  public static final String MATERIA_ID = "mid";
  public static final String MATERIA_NOMBRE = "nombre";
  public static final String MATERIA_DESCRIPCION = "descripcion";
  public static final String MATERIA_DOCENTE_MATERIA_ID = "mid";
  public static final String MATERIA_DOCENTE_DOCENTE_ID = "pid";

  // ---------------------------------------------------------------------------
  // build
  // ---------------------------------------------------------------------------
  /**
   * Construye una Materia a partir de un ResultSet previamente ejecutado.
   *
   * @param db
   * @param rs
   * @return
   * @throws Exception
   */
  protected static Materia buildMateria(DBManager db, ResultSet rs) throws Exception {
    Materia materia = null;
    if (rs.next()) {
      materia = new Materia(
        rs.getInt(MATERIA_ID),
        rs.getString(MATERIA_NOMBRE),
        rs.getString(MATERIA_DESCRIPCION)
      );
    }
    return materia;
  }

  // ---------------------------------------------------------------------------
  protected static List<Materia> buildMaterias(DBManager db, ResultSet rs) throws Exception {
    List<Materia> r = new ArrayList();

    Materia materia;
    while ((materia = buildMateria(db, rs)) != null) {
      r.add(materia);
    }

    return r;
  }

  // ---------------------------------------------------------------------------
  // load
  // ---------------------------------------------------------------------------
  /**
   * Carga una materia de la ddbb a partir de su identificador.
   *
   * @param db
   * @param id
   * @return
   * @throws Exception
   */
  public static Materia loadDB(DBManager db, Integer id) throws Exception {
    if (db == null) {
      throw new Exception("DBMateria Error: DBManager NO especificado");
    }
    if (id == null) {
      throw new Exception("DBMateria Error: Identificador NO especificado");
    }

    String query = String.format(
      "SELECT * FROM materia WHERE %s = ?", MATERIA_ID
    );

    db.prepareQuery(query);
    db.setInt(1, id);

    ResultSet rs = db.executeQuery();
    Materia materia = buildMateria(db, rs);
    rs.close();
    return materia;
  }

  // ---------------------------------------------------------------------------
  /**
   * Carga un conjunto de materias de la ddbb a partir de sus identificadores.
   *
   * @param db
   * @param ids
   * @return
   * @throws Exception
   */
  public static List<Materia> loadDB(DBManager db, Integer[] ids) throws Exception {
    if (ids == null) {
      throw new Exception("DBMateria Error: Identificadores NO especificados");
    }

    return loadList(db, ids);
  }

  // ---------------------------------------------------------------------------
  /**
   * Carga una materia de la ddbb a partir de su nombre.
   *
   * @param db
   * @param nombre
   * @return
   * @throws Exception
   */
  public static Materia loadDB(DBManager db, String nombre) throws Exception {
    if (db == null) {
      throw new Exception("DBMateria Error: DBManager NO especificado");
    }

    if (nombre == null) {
      throw new Exception("DBMateria Error: Nombre NO especificado");
    }

    String query = String.format(
      "SELECT * FROM materia m WHERE m.%s = ?", MATERIA_NOMBRE
    );

    db.prepareQuery(query);
    db.setString(1, nombre);

    ResultSet rs = db.executeQuery(query);
    Materia materia = buildMateria(db, rs);
    rs.close();
    return materia;
  }

  // ---------------------------------------------------------------------------
  // save
  // ---------------------------------------------------------------------------
  /**
   * Guarda una materia en la ddbb.
   *
   * @param db
   * @param materia
   * @return
   * @throws Exception
   */
  public static Integer saveDB(DBManager db, Materia materia) throws Exception {
    if (db == null) {
      throw new Exception("DBMateria Error: DBManager NO especificado");
    }
    if (materia == null) {
      throw new Exception("DBMateria Error: Materia NO especificado");
    }

    if (db.getDriverName().equals(DBManager.POSTGRES_DRIVER_NAME)) {
      return pgSaveDB(db, materia);
    }

    return materia.getId() == null
      ? insertDB(db, materia)
      : updateDB(db, materia);
  }

  // ---------------------------------------------------------------------------
  private static Integer insertDB(DBManager db, Materia materia) throws Exception {

    db.prepareUpdate(String.format(
      "INSERT INTO materia (%s, %s) "
      + "VALUES (?, ?)",
      MATERIA_NOMBRE, MATERIA_DESCRIPCION
    ));
    db.setString(1, materia.getNombre());
    db.setString(2, materia.getDescripcion());

    Integer id = null;
    ResultSet rs = db.executeUpdate().getGeneratedKeys();
    if (rs.first()) {
      id = rs.getInt(1);
    }
    rs.close();

    return id;
  }

  // ---------------------------------------------------------------------------
  private static Integer updateDB(DBManager db, Materia materia) throws Exception {

    db.prepareUpdate(String.format("UPDATE materia SET "
      + "%s = ?, %s = ? "
      + "WHERE %s = ?",
      MATERIA_NOMBRE, MATERIA_DESCRIPCION,
      MATERIA_ID
    ));
    db.setString(1, materia.getNombre());
    db.setString(2, materia.getDescripcion());
    db.setInt(3, materia.getId());
    db.executeUpdate();

    return materia.getId();
  }

  // ---------------------------------------------------------------------------
  private static Integer pgSaveDB(DBManager db, Materia materia) throws Exception {

    String query = "SELECT fn_savemateria(?, ?, ?)";

    db.prepareQuery(query);
    db.setInt(1, materia.getId());
    db.setString(2, materia.getNombre());
    db.setString(3, materia.getDescripcion());

    Integer id = null;
    ResultSet rs = db.executeQuery();
    if (rs.first()) {
      id = rs.getInt(1);
    }
    rs.close();

    materia.setId(id);

    return id;
  }

  // ---------------------------------------------------------------------------
  // delete
  // ---------------------------------------------------------------------------
  /**
   * Elimina una materia de la ddbb.
   *
   * @param db
   * @param id
   * @return
   * @throws Exception
   */
  public static Materia deleteDB(DBManager db, Integer id) throws Exception {
    Materia materia = loadDB(db, id);

    if (materia != null) {
      db.prepareUpdate(String.format("DELETE FROM materia WHERE %s = ?", MATERIA_ID));
      db.setInt(1, id);
      db.execute();
    }

    return materia;
  }

  // ---------------------------------------------------------------------------
  /**
   * Elimina una materia de la ddbb.
   *
   * @param db
   * @param materia
   * @return
   * @throws Exception
   */
  public static Materia deleteDB(DBManager db, Materia materia) throws Exception {
    if (materia == null) {
      throw new Exception("DBMateria Error: Materia NO especificada");
    }

    return deleteDB(db, materia.getId());
  }

  // ---------------------------------------------------------------------------
  /**
   * Elimina un conjunto de materias de la ddbb a partir de sus identificadores.
   *
   * @param db
   * @param ids
   * @return
   * @throws Exception
   */
  public static List<Materia> deleteDB(DBManager db, Integer[] ids) throws Exception {
    List<Materia> materias = loadList(db, ids);

    if (materias != null) {
      db.prepareUpdate(String.format("DELETE FROM materia WHERE %s IN (?)", MATERIA_ID));
      db.setArray(1, "INTEGER", ids);
      db.execute();
    }

    return materias;
  }

  // ---------------------------------------------------------------------------
  /**
   * Elimina un conjunto de materias de la ddbb.
   *
   * @param db
   * @param materias
   * @return
   * @throws Exception
   */
  public static List<Materia> deleteDB(DBManager db, Materia[] materias) throws Exception {
    int l = materias.length;
    Integer[] ids = new Integer[l];

    for (int i = 0; i < l; i++) {
      ids[i] = materias[i].getId();
    }

    return deleteDB(db, ids);
  }

  // ---------------------------------------------------------------------------
  // list
  // ---------------------------------------------------------------------------
  /**
   * Genera una lista de materias.
   *
   * @param db
   * @param ids
   * @param limit
   * @param offset
   * @return
   * @throws Exception
   */
  public static List<Materia> loadList(DBManager db, Integer[] ids, int limit, int offset) throws Exception {
    if (db == null) {
      throw new Exception("DBMateria Error: DBManager NO especificado");
    }
    if (limit < 0) {
      throw new Exception("DBMateria Error: limit incorrecto");
    }
    if (offset < 0) {
      throw new Exception("DBMateria Error: offset incorrecto");
    }

    String query = DBManager.buildSelectQuery(
      "*",
      "materia m",
      ids == null ? null : String.format("m.%s IN (?)", MATERIA_ID),
      null, null,
      String.format("m.%s", MATERIA_NOMBRE),
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
    List<Materia> materias = buildMaterias(db, rs);
    rs.close();

    return materias;
  }

  // ---------------------------------------------------------------------------
  public static List<Materia> loadList(DBManager db, Integer[] ids) throws Exception {
    return loadList(db, ids, 0, 0);
  }

  // ---------------------------------------------------------------------------
  public static List<Materia> loadList(DBManager db, int limit, int offset) throws Exception {
    return loadList(db, null, limit, offset);
  }

  // ---------------------------------------------------------------------------
  public static List<Materia> loadList(DBManager db) throws Exception {
    return loadList(db, null);
  }

  // ---------------------------------------------------------------------------
  // docentes asociados: load
  // ---------------------------------------------------------------------------
  /**
   * Carga los docentes asociados a una materia.
   *
   * @param db
   * @param materia
   * @param limit
   * @param offset
   * @return
   * @throws Exception
   */
  public static List<Docente> loadDocentes(DBManager db, Materia materia, int limit, int offset) throws Exception {
    if (db == null) {
      throw new Exception("DBMateria Error: DBManager NO especificado");
    }
    if (materia == null) {
      throw new Exception("DBMateria Error: Materia NO especificada");
    }

    String query = DBManager.buildSelectQuery(String.format(
      "p.%s, p.%s, p.%s, p.%s, d.%s",
      DBPersona.PERSONA_ID, DBPersona.PERSONA_DNI,
      DBPersona.PERSONA_APELLIDO, DBPersona.PERSONA_NOMBRE,
      DBDocente.DOCENTE_LEGAJO
    ),
      String.format("persona p "
        + "JOIN docente d ON p.%s = d.%s "
        + "JOIN materia_docentes md ON d.%s = md.%s",
        DBPersona.PERSONA_ID, DBDocente.DOCENTE_ID,
        DBDocente.DOCENTE_ID, MATERIA_DOCENTE_DOCENTE_ID
      ),
      String.format("md.%s = ?", MATERIA_DOCENTE_MATERIA_ID),
      null, null,
      String.format("p.%s, p.%s", DBPersona.PERSONA_APELLIDO, DBPersona.PERSONA_NOMBRE),
      limit, offset
    );

    db.prepareQuery(query);
    db.setInt(1, materia.getId());

    ResultSet rs = db.executeQuery();
    List<Docente> docentes = DBDocente.buildDocentes(rs);
    rs.close();

    materia.setDocentes(docentes);
    return docentes;
  }

  // ---------------------------------------------------------------------------
  public static List<Docente> loadDocentes(DBManager db, Materia materia) throws Exception {
    return loadDocentes(db, materia, 0, 0);
  }

  // ---------------------------------------------------------------------------
  // docentes asociados: save
  // ---------------------------------------------------------------------------
  public static void saveDocentes(DBManager db, Materia materia) throws Exception {
    if (db == null) {
      throw new Exception("DBMateria Error: DBManager NO especificado");
    }
    if (materia == null) {
      throw new Exception("DBMateria Error: Materia NO especificada");
    }

    db.prepareUpdate(String.format(
      "DELETE FROM materia_docentes md WHERE md.%s = ?", MATERIA_DOCENTE_MATERIA_ID
    ));
    db.setInt(1, materia.getId());
    db.executeUpdate();

    if (materia.getDocentes().size() > 0) {
      db.prepareUpdate(String.format(
        "INSERT INTO materia_docentes (%s, %s) VALUES (?, ?)",
        MATERIA_DOCENTE_MATERIA_ID, MATERIA_DOCENTE_DOCENTE_ID
      ));

      for (Docente docente : materia.getDocentes()) {
        db.setInt(1, materia.getId());
        db.setInt(2, docente.getId());
        db.executeUpdate();
      }
    }
  }

  // ---------------------------------------------------------------------------
  // candidatos (docentes NO asociados): load
  // ---------------------------------------------------------------------------
  /**
   * Carga los docentes NO asociados a una materia.
   *
   * @param db
   * @param materia
   * @param limit
   * @param offset
   * @return
   * @throws Exception
   */
  public static List<Docente> loadCandidatos(DBManager db, Materia materia, int limit, int offset) throws Exception {
    if (db == null) {
      throw new Exception("DBMateria Error: DBManager NO especificado");
    }
    if (materia == null) {
      throw new Exception("DBMateria Error: Materia NO especificada");
    }

    String query = DBManager.buildSelectQuery(String.format(
      "p.%s, p.%s, p.%s, d.%s, d.%s",
      DBPersona.PERSONA_ID, DBPersona.PERSONA_DNI,
      DBPersona.PERSONA_APELLIDO, DBPersona.PERSONA_NOMBRE,
      DBDocente.DOCENTE_LEGAJO
    ),
      String.format("persona p "
        + "JOIN docente d ON p.%s = d.%s ",
        DBPersona.PERSONA_ID, DBDocente.DOCENTE_ID
      ),
      String.format("d.%s NOT IN (%s)",
        DBDocente.DOCENTE_ID,
        DBManager.buildSelectQuery(
          String.format("md.%s", MATERIA_DOCENTE_DOCENTE_ID),
          "materia_docentes md",
          String.format("md.%s = ?", MATERIA_DOCENTE_MATERIA_ID),
          null, null, null
        )
      ),
      null, null,
      String.format("p.%s, p.%s", DBPersona.PERSONA_APELLIDO, DBPersona.PERSONA_NOMBRE),
      limit, offset
    );

    db.prepareQuery(query);
    db.setInt(1, materia.getId());

    ResultSet rs = db.executeQuery();
    List<Docente> docentes = DBDocente.buildDocentes(rs);
    rs.close();

    materia.setDocentes(docentes);
    return docentes;
  }

  // ---------------------------------------------------------------------------
  public static List<Docente> loadCandidatos(DBManager db, Materia materia) throws Exception {
    return loadCandidatos(db, materia, 0, 0);
  }
  
  // ---------------------------------------------------------------------------
  // asociaciones: add (¿tiene sentido?)
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  // asociaciones: remove
  // ---------------------------------------------------------------------------
  /**
   * Disocia un docente de la materia especificada.
   *
   * @param db
   * @param materia
   * @param docente
   * @throws Exception
   */
  public static void deleteAsociacion(DBManager db, Materia materia, Docente docente) throws Exception {
    if (db == null) {
      throw new Exception("DBMateria Error: DBManager NO especificado");
    }
    if (materia == null) {
      throw new Exception("DBMateria Error: Materia NO especificada");
    }
    if (docente == null) {
      throw new Exception("DBMateria Error: Docente NO especificado");
    }

    db.prepareUpdate(String.format(
      "DELETE FROM materia_docentes WHERE %s = ? AND %s = ?",
      MATERIA_DOCENTE_MATERIA_ID, MATERIA_DOCENTE_DOCENTE_ID
    ));

    db.setInt(1, materia.getId());
    db.setInt(2, docente.getId());
    db.executeQuery();
  }

}
