/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.lib.domain.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.apache.openjpa.persistence.jdbc.FetchMode;
import org.apache.openjpa.persistence.jdbc.SubclassFetchMode;
//import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author scarafia
 */
@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
@SubclassFetchMode(FetchMode.NONE)
@PrimaryKeyJoinColumn(name = "pid")
@Table(name = "alumno")
//@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Alumno.findAll", query = "SELECT a FROM Alumno a"),
  @NamedQuery(name = "Alumno.findAllById", query = "SELECT a FROM Alumno a ORDER BY a.id"),
  @NamedQuery(name = "Alumno.findAllByApeNom", query = "SELECT a FROM Alumno a ORDER BY a.apellido, a.nombre"),
  @NamedQuery(name = "Alumno.findById", query = "SELECT a FROM Alumno a WHERE a.id = :id"),
  @NamedQuery(name = "Alumno.findByLegajo", query = "SELECT a FROM Alumno a WHERE a.legajo = :legajo"),
  @NamedQuery(name = "Alumno.findByLegajo", query = "SELECT a FROM Alumno a WHERE a.legajo = :legajo")
})
public class Alumno extends Persona implements Serializable {
  
  public static void insert(EntityManager em, Integer id, String legajo) {
    String query = "insert into alumno (pid, legajo) values (?, ?)";
    em.createNativeQuery(query).setParameter(1, id).setParameter(2, legajo).executeUpdate();
  }
  public static void delete(EntityManager em, Integer id) {
    String query = "delete from alumno where pid = ?";
    em.createNativeQuery(query).setParameter(1, id).executeUpdate();
  }

  private static final long serialVersionUID = 1L;
  
  @Basic(optional = false)
  @Column(name = "legajo", length = 16)
  private String legajo;

  public Alumno() {
    super();
  }

  public Alumno(Integer id, String dni, String apellido, String nombre, String legajo) {
    super(id, dni, apellido, nombre);
    this.legajo = legajo;
  }

  public Alumno(String dni, String apellido, String nombre, String legajo) {
    this(null, dni, apellido, nombre, legajo);
  }
  
  public Alumno(Persona persona, String legajo) {
    this(persona.id, persona.dni, persona.apellido, persona.nombre, legajo);
  }

  public String getLegajo() {
    return legajo;
  }

  public void setLegajo(String legajo) {
    this.legajo = legajo;
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
    if (!(object instanceof Alumno)) {
      return false;
    }
    Alumno other = (Alumno) object;
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
