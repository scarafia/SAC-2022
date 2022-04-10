/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.run.jpa;

import java.util.Scanner;
import utn.frc.dlc.tutor.sac.lib.util.menu.Menu;
import utn.frc.dlc.tutor.sac.lib.util.menu.MenuItem;

/**
 *
 * @author scarafia
 */
public abstract class JPA {
  
  // PostgreSQL
  public static final String PG_PERSISTENCE_UNIT_NAME = "pgPU";
  
  // H2 Database Engine
  public static final String H2_PERSISTENCE_UNIT_NAME = "h2PU";
  
  // PostgreSQL
  public static final String MY_PERSISTENCE_UNIT_NAME = "myPU";
  
  // Run
  public static void staticRun(Scanner sc) {
    
    MenuItem[] ops = {
      new MenuItem("1", "JPA. Alumnos") {
        @Override
        public void execute() {
          JPAAlumno.staticRun();
        }
      },
      new MenuItem("2", "JPA. Docentes") {
        @Override
        public void execute() {
          JPADocente.staticRun();
        }
      },
      new MenuItem("3", "JPA. Personas") {
        @Override
        public void execute() {
          JPAPersona.staticRun(sc);
        }
      },
    };
    
    Menu menu = new Menu(sc, ops);
    
    menu.run();
    
  }

}
