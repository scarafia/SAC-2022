/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.lib.util;

/**
 *
 * @author scarafia
 */
public abstract class Util {
  
  public static String lPad(String text, int len) {
    return String.format("%" + len + "s", text);
  }
  
  public static String lPad(Object o, int len) {
    return lPad(o.toString(), len);
  }
  
  public static String rPad(String text, int len) {
    return String.format("%-" + len + "s", text);
  }
  
  public static String rPad(Object o, int len) {
    return rPad(o.toString(), len);
  }
  
  public static String fill(char caracter, int len) {
    return lPad("", len).replace(' ', caracter);
  }
  
  public static boolean matchNumeric(String arg) {
    return arg.matches("^-?\\d+(\\.\\d+)?$");
  }
  
  public static boolean matchInteger(String arg) {
    return arg.matches("^-?\\d+$");
  }
  
  public static boolean isMultipleOf(int sub, int mult) {
    return (mult % sub) == 0;
  }
  
  public static boolean isEven(int n) {
    return isMultipleOf(2, n);
  }
  
  public static boolean isOdd(int n) {
    return (!isEven(n));
  }
  
  public static boolean isPrime(int n) {
    if (n < 0) {
      n = n * -1;
    }
    
    for (int i = 1, c = 0; i <= n/2; i++) {
      if (isMultipleOf(i, n)) {
        if (++c == 2) {
          return false;
        }
      }
    }
    
    return true;
  }
  
}
