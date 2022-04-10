/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.lib.domain.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author scarafia
 */
@Entity
@Table(name = "curso")
//@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c"),
  @NamedQuery(name = "Curso.findById", query = "SELECT c FROM Curso c WHERE c.id = :id"),
  @NamedQuery(name = "Curso.findByNombre", query = "SELECT c FROM Curso c WHERE c.nombre = :nombre"),
  @NamedQuery(name = "Curso.findByDescripcion", query = "SELECT c FROM Curso c WHERE c.descripcion = :descripcion"),
  @NamedQuery(name = "Curso.findByCupo", query = "SELECT c FROM Curso c WHERE c.cupo = :cupo"),
  @NamedQuery(name = "Curso.findByFinicio", query = "SELECT c FROM Curso c WHERE c.fInicio = :fInicio"),
  @NamedQuery(name = "Curso.findByFfin", query = "SELECT c FROM Curso c WHERE c.fFin = :fFin")})
public class Curso implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "cid")
  private Integer id;
  @Basic(optional = false)
  @Column(name = "nombre", length = 64)
  private String nombre;
  @Column(name = "descripcion", length = 2147483647)
  private String descripcion;
  @Basic(optional = false)
  @Column(name = "cupo")
  private short cupo;
  @Basic(optional = false)
  @Column(name = "finicio")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fInicio;
  @Column(name = "ffin")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fFin;
  
  // Integridad ya gestionada por foreign keys en la dddbb.
  //@JoinColumns({
  //  @JoinColumn(name = "mid", referencedColumnName = "mid"),
  //  @JoinColumn(name = "pid", referencedColumnName = "pid")})
  //@ManyToOne(optional = false)
  //private MateriaDocentes materiaDocentes;
  
  @JoinColumn(name = "mid", referencedColumnName = "mid")
  @ManyToOne(optional = false)
  private Materia materia;
  
  @JoinColumn(name = "pid", referencedColumnName = "pid")
  @ManyToOne(optional = false)
  private Docente docente;
  
  @JoinTable(
    name = "curso_alumnos",
    joinColumns = {
      @JoinColumn(name = "cid", referencedColumnName = "cid")
    },
    inverseJoinColumns = {
      @JoinColumn(name = "pid", referencedColumnName = "pid")
    }
  )
  @ManyToMany
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
    this.fInicio = fInicio;
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
