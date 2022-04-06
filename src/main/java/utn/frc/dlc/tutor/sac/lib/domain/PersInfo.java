/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.lib.domain;

/**
 *
 * @author scarafia
 */
public class PersInfo extends Persona {
  private String legAlumno;
  private String legDocente;
  
  public PersInfo() {
    super();
  }
  
  public PersInfo(
          Integer id, String dni, String apellido, String nombre,
          String legAlumno, String legDocente) {
    super(id, dni, apellido, nombre);
    this.legAlumno = legAlumno;
    this.legDocente = legDocente;
  }

  public String getLegAlumno() {
    return legAlumno;
  }

  public void setLegAlumno(String legAlumno) {
    this.legAlumno = legAlumno;
  }

  public String getLegDocente() {
    return legDocente;
  }

  public void setLegDocente(String legDocente) {
    this.legDocente = legDocente;
  }
  
}
