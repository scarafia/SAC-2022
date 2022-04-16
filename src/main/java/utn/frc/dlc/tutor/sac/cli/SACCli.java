/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.cli;

import utn.frc.dlc.tutor.sac.run.jdbc.JDBC;
import java.util.Scanner;
import utn.frc.dlc.tutor.sac.lib.util.menu.Menu;
import utn.frc.dlc.tutor.sac.lib.util.menu.MenuItem;

/**
 *
 * @author scarafia
 */
public class SACCli {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    staticRun(sc);

    sc.close();

  }

  private static void staticRun(Scanner sc) {

    MenuItem[] ops = {
      new MenuItem("1", "JDBC") {
        @Override
        public void execute() {
          JDBC.staticRun(sc);
        }
      },
    };

    Menu menu = new Menu(sc, ops);

    menu.run();

  }

}
