/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.lib.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
//import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author scarafia
 */
//@XmlRootElement
public class Curso implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private Integer id;
  private String nombre;
  private String descripcion;
  private short cupo;
  private Date fInicio;
  private Date fFin;
  
  private Materia materia;
  private Docente docente;
  
  private Collection<Alumno> alumnos;
  
  public Curso() {
    super();
  }

  public Curso(Integer id, String nombre, String descripcion, short cupo, Date fInicio, Date fFin, Materia materia, Docente docente) {
    this();
    this.id = id;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.cupo = cupo;
    this.fInicio = fInicio;
    this.fFin = fFin;
    this.materia = materia;
    this.docente = docente;
  }
  
  public Curso(String nombre, String descripcion, short cupo, Date fInicio, Date fFin, Materia materia, Docente docente) {
    this(null, nombre, descripcion, cupo, fInicio, fFin, materia, docente);
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

  public short getCupo() {
    return cupo;
  }

  public void setCupo(short cupo) {
    this.cupo = cupo;
  }

  public Date getFInicio() {
    return fInicio;
  }

  public void setFInicio(Date fInicio) {
    this.fInicio = fInicio;
  }

  public Date getFFin() {
    return fFin;
  }

  public void setFFin(Date fFin) {
    this.fFin = fFin;
  }
  
  public Materia getMateria() {
    return materia;
  }
  
  public void setMateria(Materia materia) {
    this.materia = materia;
  }
  
  public Docente getDocente() {
    return docente;
  }
  
  public void setDocente(Docente docente) {
    this.docente = docente;
  }
  
  public Collection<Alumno> getAlumnos() {
    return alumnos;
  }
  
  public void setAlumnos(Collection<Alumno> alumnos) {
    this.alumnos = alumnos;
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
    if (!(object instanceof Curso)) {
      return false;
    }
    Curso other = (Curso) object;
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
