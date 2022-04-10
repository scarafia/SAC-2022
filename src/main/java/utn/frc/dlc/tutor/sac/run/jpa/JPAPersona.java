/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.run.jpa;

import utn.frc.dlc.tutor.sac.lib.domain.jpa.Persona;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author scarafia
 */
public abstract class JPAPersona {

  private static void listPersonas(List<Persona> personas) {

    System.out.println("Listando personas:");
    System.out.println("=================");

    for (Persona persona : personas) {
      System.out.println(persona);
    }

  }

  private static void runBatch(EntityManager em) {

    List<Persona> personas = em.createNamedQuery("Persona.findAllById").getResultList();
    listPersonas(personas);

    System.out.println("Creando nueva persona...");
    Persona persona = new Persona("dni999", "Lojuno", "Johny");
    System.out.println("Nueva persona creada: " + persona);
    
    em.getTransaction().begin();
    em.persist(persona);
    em.getTransaction().commit();
    Integer id = persona.getId();
    System.out.println("Nueva persona guardada: " + persona);

    persona = em.createNamedQuery("Persona.findById", Persona.class).setParameter("id", id).getSingleResult();
    System.out.println("Nueva persona cargada: " + persona);

    persona.setApellido("Quito");
    persona.setNombre("Armando Esteban");
    System.out.println("Nueva persona modificada: " + persona);

    em.getTransaction().begin();
    em.persist(persona);
    em.getTransaction().commit();
    id = persona.getId();
    System.out.println("Nueva persona guardada: " + persona);

    persona = em.createNamedQuery("Persona.findById", Persona.class).setParameter("id", id).getSingleResult();
    System.out.println("Nueva persona cargada: " + persona);
    
    em.getTransaction().begin();
    persona.setApellido("Chuca");
    persona.setNombre("Kevin");
    em.getTransaction().commit();
    id = persona.getId();
    System.out.println("Nueva persona modificada (y guardada): " + persona);
    
    persona = em.createNamedQuery("Persona.findById", Persona.class).setParameter("id", id).getSingleResult();
    System.out.println("Nueva persona cargada: " + persona);
    
    em.getTransaction().begin();
    em.remove(persona);
    em.getTransaction().commit();
    System.out.println("Nueva persona eliminada: " + persona);
    
    //persona = em.createNamedQuery("Persona.findById", Persona.class).setParameter("id", id).getSingleResult();
    persona = em.find(Persona.class, id);
    id = persona == null ? null : persona.getId();
    System.out.println("Se intenta cargar nuevamente: (" + id + ") " + persona);
    
  }

  public static void staticRun(Scanner sc) {
    boolean test = false;
    if (test) {
      staticTest(sc);
      return;
    }

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

  private static void staticTest(Scanner sc) {
    System.out.println("Testing...");
    System.out.println("-----------------------------------------------------");

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pgPU");
    EntityManager em = emf.createEntityManager();

    // TODO here ---------------------------------------------------------------
    List<Persona> personas;
//    List<Docente> docentes;
//    List<Alumno> alumnos;
    List list;

    Persona p;
//    Docente d;
//    Alumno a;

//    p = em.find(Persona.class, 8);
//    System.out.println(p);

    p = (Persona) em.createNamedQuery("Persona.findById").setParameter("id", 1).getSingleResult();
    System.out.println(p);

//    d = em.find(Docente.class, 8);
//    System.out.println(d);

//    a = em.find(Alumno.class, 5);
//    System.out.println(a);

    // -------------------------------------------------------------------------
    //list.forEach(element -> {
    //  System.out.println(element);
    //});

//    personas = em.createNamedQuery("Persona.findAllById").getResultList();
//    list = personas;
//    list.forEach(System.out::println);

//    List<PersInfo2> p2 = em.createNamedQuery("PersInfo2.personaList").getResultList();
//    list = personas;
//    list.forEach(System.out::println);

//    list = docentes;
//    list.forEach(System.out::println);
//
//    list = alumnos;
//    list.forEach(System.out::println);

    // -------------------------------------------------------------------------
//    em.getTransaction().begin();
//    p = em.find(Persona.class, 27);
//    d = em.find(Docente.class, 27);
//    p.setApellido("QQQQQQ");
//    d.setApellido("PPPPPPP");
//    d.setLegajo("Leg27");
//    em.getTransaction().commit();

    // TODO here ---------------------------------------------------------------

    em.close();
    emf.close();
  }

}
