/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.lib.util.menu;

/**
 *
 * @author scarafia
 */
public abstract class MenuItem implements MenuCall {
  private final String key;
  private final String text;
  
  public MenuItem(String key, String text) {
    this.key = key;
    this.text = text;
  }

  /**
   * @return the key
   */
  public String getKey() {
    return key;
  }

  /**
   * @return the Text
   */
  public String getText() {
    return text;
  }
  
  @Override
  public abstract void execute();
}
