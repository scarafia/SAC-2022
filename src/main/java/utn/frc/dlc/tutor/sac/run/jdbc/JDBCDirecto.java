/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.run.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import utn.frc.dlc.tutor.sac.lib.domain.Alumno;
import utn.frc.dlc.tutor.sac.lib.domain.Persona;

/**
 *
 * @author scarafia
 */
public abstract class JDBCDirecto {
  
  // PostgreSQL
  private static void pg_dbquery() throws Exception, ClassNotFoundException {

    Class.forName(JDBC.PG_DRIVER_NAME);
    Connection cn = DriverManager.getConnection(JDBC.PG_URL, JDBC.PG_USR, JDBC.PG_PWD);

//    Statement stmt = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    Statement stmt = cn.createStatement();

    String query = "SELECT * FROM persona ORDER BY apellido, nombre";
    ResultSet rs = stmt.executeQuery(query);

    while (rs.next()) {
      Integer id = rs.getInt("pid");
      String dni = rs.getString("dni");
      String ape = rs.getString("apellido");
      String nom = rs.getString("nombre");
      Persona p = new Persona(id, dni, ape, nom);
      System.out.println(p);
    }

    rs.close();

    stmt.close();

    cn.close();

  }

  // H2 Database Engine
  private static void h2_dbquery() throws Exception, ClassNotFoundException {

    Class.forName(JDBC.H2_DRIVER_NAME);
    Connection cn = DriverManager.getConnection(JDBC.H2_URL);

    Statement stmt = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

    String query = "SELECT * FROM persona ORDER BY apellido, nombre";
    ResultSet rs = stmt.executeQuery(query);

    while (rs.next()) {
      Integer id = rs.getInt("pid");
      String dni = rs.getString("dni");
      String ape = rs.getString("apellido");
      String nom = rs.getString("nombre");
      Persona p = new Persona(id, dni, ape, nom);
      System.out.println(p);
    }

    rs.close();

    stmt.close();

    cn.close();

  }

  public static void staticRun() {
    try {
      System.out.println("-------------------------");
      System.out.println("Consultando PostgreSQL...");
      System.out.println("-------------------------");
      pg_dbquery();
      System.out.println("---------------------------------");
      System.out.println("Consultando H2 Database Engine...");
      System.out.println("---------------------------------");
//      h2_dbquery();
    } catch (ClassNotFoundException e) {
      System.err.println("No se encontró la clase");
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

}
