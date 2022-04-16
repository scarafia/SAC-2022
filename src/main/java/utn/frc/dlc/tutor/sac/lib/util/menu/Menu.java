/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.lib.util.menu;

import java.util.Scanner;
import utn.frc.dlc.tutor.sac.lib.util.Util;

/**
 *
 * @author scarafia
 */
public class Menu {
  
  private Scanner sc;
  private String question;
  private MenuItem[] items;
  private String exitKey, exitText;
  
  public Menu(Scanner sc, String question, MenuItem[] items, String exitKey, String exitText) {
    this.sc = sc;
    this.question = question;
    this.items = items;
    this.exitKey = exitKey;
    this.exitText = exitText;
  }
  
  public Menu(Scanner sc, String question, MenuItem[] items) {
    this(sc, question, items, "q", "Salir");
  }
  
  public Menu(Scanner sc, MenuItem[] items) {
    this (sc, "Seleccione una opción: ", items);
  }
  
  private void printQuestion() {
    System.out.println(question);
  }
  
  private void printOptions() {
    for (MenuItem item : items) {
      System.out.println(item.getKey() + ": " + item.getText());
    }
    
    System.out.println(exitKey + ": " + exitText);
  }
  
  private String getSelection() {
    return sc.nextLine();
  }
  
  private MenuItem getItem(String key) {
    for (MenuItem item : items) {
      if (key.equals(item.getKey())) {
        return item;
      }
    }
    return null;
  }
  
  public void run() {
    
    while (true) {
      
      printQuestion();
      
      printOptions();
      
      String op = getSelection();
      
      if (op.equals(exitKey)) {
        break;
      }
      
      MenuItem task = getItem(op);
      
      if (task != null) {
        task.execute();
      } else {
        System.out.println("Opción incorrecta.");
      }
    }
  }
  
  // Static functions
  
  private static void printQuestion(String question) {
    System.out.println(question);
  }
  
  private static String getAnswer(Scanner sc) {
    return sc.nextLine();
  }
  
  public static String ask(Scanner sc, String question) {
    printQuestion(question);
    return getAnswer(sc);
  }
  
  public static int askInt(Scanner sc, String question) {
    while (true) {
      String answer = ask(sc, question);
      if (Util.matchInteger(answer)) {
        return Integer.parseInt(answer);
      } else {
        System.out.println("El valor ingresado NO es un entero.");
      }
    }
  }

  public static boolean confirm(Scanner sc, String question, String yesKey, String noKey) {
    String answer = "";
    
    while (!(answer.equals(yesKey) || answer.equals(noKey))) {
      answer = ask(sc, question + "(" + yesKey + "/" + noKey + ")");
    }
    
    return answer.equals(yesKey);
  }
  
  public static boolean confirm(Scanner sc, String question) {
    return confirm(sc, question, "s", "n");
  }
  
}
