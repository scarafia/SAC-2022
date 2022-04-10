/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.run.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utn.frc.dlc.tutor.sac.lib.domain.jpa.Alumno;

/**
 *
 * @author scarafia
 */
public abstract class JPAAlumno {

  private static void listAlumnos(List<Alumno> alumnos) {

    System.out.println("Listando alumnos:");
    System.out.println("=================");

    for (Alumno alumno : alumnos) {
      System.out.println(alumno);
    }

  }

  private static void runBatch(EntityManager em) {

    List<Alumno> alumnos = em.createNamedQuery("Alumno.findAllById").getResultList();
    listAlumnos(alumnos);

    System.out.println("Creando nuevo alumno...");
    Alumno alumno = new Alumno("dni999", "Lojuno", "Johny", "leg999");
    System.out.println("Nuevo alumno creado: " + alumno);
    
    em.getTransaction().begin();
    em.persist(alumno);
    em.getTransaction().commit();
    Integer id = alumno.getId();
    System.out.println("Nuevo alumno guardado: " + alumno);

    alumno = em.createNamedQuery("Alumno.findById", Alumno.class).setParameter("id", id).getSingleResult();
    System.out.println("Nuevo alumno cargado: " + alumno);

    alumno.setApellido("Quito");
    alumno.setNombre("Armando Esteban");
    System.out.println("Nuevo alumno modificado: " + alumno);

    em.getTransaction().begin();
    em.persist(alumno);
    em.getTransaction().commit();
    id = alumno.getId();
    System.out.println("Nuevo alumno guardado: " + alumno);

    alumno = em.createNamedQuery("Alumno.findById", Alumno.class).setParameter("id", id).getSingleResult();
    System.out.println("Nuevo alumno cargado: " + alumno);
    
    em.getTransaction().begin();
    alumno.setApellido("Chuca");
    alumno.setNombre("Kevin");
    em.getTransaction().commit();
    id = alumno.getId();
    System.out.println("Nuevo alumno modificado (y guardado): " + alumno);
    
    alumno = em.createNamedQuery("Alumno.findById", Alumno.class).setParameter("id", id).getSingleResult();
    System.out.println("Nuevo alumno cargado: " + alumno);
    
    em.getTransaction().begin();
    em.remove(alumno);
    em.getTransaction().commit();
    System.out.println("Nuevo alumno eliminado: " + alumno);
    
    //alumno = em.createNamedQuery("Alumno.findById", Alumno.class).setParameter("id", id).getSingleResult();
    alumno = em.find(Alumno.class, id);
    id = alumno == null ? null : alumno.getId();
    System.out.println("Se intenta cargar nuevamente: (" + id + ") " + alumno);
    
  }

  public static void staticRun() {

    EntityManagerFactory emf;
    EntityManager em;

    // PostgreSQL
    System.out.println("---------------------------------");
    System.out.println("Conectando PostgreSQL...");
    System.out.println("---------------------------------");

    emf = Persistence.createEntityManagerFactory(JPA.PG_PERSISTENCE_UNIT_NAME);
    em = emf.createEntityManager();

    runBatch(em);

    em.close();
    emf.close();

    // H2 Database Engine
    System.out.println("---------------------------------");
    System.out.println("Conectando H2 Database Engine...");
    System.out.println("---------------------------------");

    emf = Persistence.createEntityManagerFactory(JPA.H2_PERSISTENCE_UNIT_NAME);
    em = emf.createEntityManager();

    runBatch(em);

    em.close();
    emf.close();
    
    // MySQL
    System.out.println("---------------------------------");
    System.out.println("Conectando MySQL...");
    System.out.println("---------------------------------");

    emf = Persistence.createEntityManagerFactory(JPA.MY_PERSISTENCE_UNIT_NAME);
    em = emf.createEntityManager();

    runBatch(em);

    em.close();
    emf.close();

  }

}
