/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.lib.domain;

import java.io.Serializable;
import java.util.Collection;
//import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author scarafia
 */
//@XmlRootElement
public class Docente extends Persona implements Serializable {

  private static final long serialVersionUID = 1L;

  private String legajo;
  private Collection<Materia> materias;
  
  public Docente() {
    super();
  }

  public Docente(Integer id, String dni, String apellido, String nombre, String legajo) {
    super(id, dni, apellido, nombre);
    this.legajo = legajo;
  }

  public Docente(String dni, String apellido, String nombre, String legajo) {
    this(null, dni, apellido, nombre, legajo);
  }
  
  public Docente(Persona persona, String legajo) {
    this(persona.id, persona.dni, persona.apellido, persona.nombre, legajo);
  }

  public String getLegajo() {
    return legajo;
  }

  public void setLegajo(String legajo) {
    this.legajo = legajo;
  }

  public Collection<Materia> getMaterias() {
    return materias;
  }
  
  public void setMaterias(Collection<Materia> materias) {
    this.materias = materias;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Docente)) {
      return false;
    }
    Docente other = (Docente) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    //return super.toString() + String.format(" [%s]", legajo);
    return String.format("(%3d) %s, %s [%s]", id, apellido, nombre, legajo);
  }

}
