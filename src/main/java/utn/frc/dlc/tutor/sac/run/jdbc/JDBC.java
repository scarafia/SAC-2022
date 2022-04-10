/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.run.jdbc;

import java.util.Scanner;
import utn.frc.dlc.tutor.sac.lib.util.menu.Menu;
import utn.frc.dlc.tutor.sac.lib.util.menu.MenuItem;

/**
 *
 * @author scarafia
 */
public abstract class JDBC {
  
  // PostgreSQL
  public static final String PG_DRIVER_NAME = "org.postgresql.Driver";
  public static final String PG_URL = "jdbc:postgresql://localhost:5432/sacdb";
//  public static final String PG_URL = "jdbc:postgresql://pg96:5432/sacdb";
  public static final String PG_USR = "sacusr";
  public static final String PG_PWD = "sacpwd";
  
  // H2 Database Engine
  public static final String H2_DRIVER_NAME = "org.h2.Driver";
  public static final String H2_URL = "jdbc:h2:~/dbdata/dlc2022/sacdb";

  // Run
  public static void staticRun(Scanner sc) {
    
    MenuItem[] ops = {
      new MenuItem("1", "JDBC Directo") {
        @Override
        public void execute() {
          JDBCDirecto.staticRun();
        }
      },
      new MenuItem("2", "JDBC Con DBManager") {
        @Override
        public void execute() {
          JDBCconDBManager.staticRun();
        }
      },
    };
    
    Menu menu = new Menu(sc, ops);
    
    menu.run();
  }
  
}
