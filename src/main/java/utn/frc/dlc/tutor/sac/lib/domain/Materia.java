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
public class Materia implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private Integer id;
  private String nombre;
  private String descripcion;
  
  private Collection<Docente> docentes;

  public Materia() {
    super();
  }

  public Materia(Integer id, String nombre, String descripcion) {
    this();
    this.id = id;
    this.nombre = nombre;
    this.descripcion = descripcion;
  }
  
  public Materia(String nombre, String descripcion) {
    this(null, nombre, descripcion);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Collection<Docente> getDocentes() {
    return docentes;
  }

  public void setDocentes(Collection<Docente> docentes) {
    this.docentes = docentes;
  }
  
  public int getCantDocentes() {
    return docentes.size();
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
    if (!(object instanceof Materia)) {
      return false;
    }
    Materia other = (Materia) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return String.format("(%2d) %s", id, nombre);
  }
  
}
