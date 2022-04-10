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
import javax.print.Doc;

import utn.frc.dlc.tutor.sac.lib.domain.jpa.Docente;

/**
 *
 * @author scarafia
 */
public abstract class JPADocente {

  private static void listDocentes(List<Docente> docentes) {

    System.out.println("Listando docentes:");
    System.out.println("=================");

    for (Docente docente : docentes) {
      System.out.println(docente);
    }

  }

  private static void runBatch(EntityManager em) {

    List<Docente> docentes = em.createNamedQuery("Docente.findAllById").getResultList();
    listDocentes(docentes);

    System.out.println("Creando nuevo docente...");
    Docente docente = new Docente("dni999", "Lojuno", "Johny", "leg999");
    System.out.println("Nuevo docente creado: " + docente);
    
    em.getTransaction().begin();
    em.persist(docente);
    em.getTransaction().commit();
    Integer id = docente.getId();
    System.out.println("Nuevo docente guardado: " + docente);

    docente = em.createNamedQuery("Docente.findById", Docente.class).setParameter("id", id).getSingleResult();
    System.out.println("Nuevo docente cargado: " + docente);

    docente.setApellido("Quito");
    docente.setNombre("Armando Esteban");
    System.out.println("Nuevo docente modificado: " + docente);

    em.getTransaction().begin();
    em.persist(docente);
    em.getTransaction().commit();
    id = docente.getId();
    System.out.println("Nuevo docente guardado: " + docente);

    docente = em.createNamedQuery("Docente.findById", Docente.class).setParameter("id", id).getSingleResult();
    System.out.println("Nuevo docente cargado: " + docente);
    
    em.getTransaction().begin();
    docente.setApellido("Chuca");
    docente.setNombre("Kevin");
    em.getTransaction().commit();
    id = docente.getId();
    System.out.println("Nuevo docente modificado (y guardado): " + docente);
    
    docente = em.createNamedQuery("Docente.findById", Docente.class).setParameter("id", id).getSingleResult();
    System.out.println("Nuevo docente cargado: " + docente);
    
    em.getTransaction().begin();
    em.remove(docente);
    em.getTransaction().commit();
    System.out.println("Nuevo docente eliminado: " + docente);
    
    //docente = em.createNamedQuery("Docente.findById", Docente.class).setParameter("id", id).getSingleResult();
    docente = em.find(Docente.class, id);
    id = docente == null ? null : docente.getId();
    System.out.println("Se intenta cargar nuevamente: (" + id + ") " + docente);
    
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
