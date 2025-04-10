/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.provajavafx.hibernateexample;

import Model.Persona;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


/**
 *
 * @author gualb
 */
public class HibernateExample {

    public static void main(String[] args) {
         SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Persona p = new Persona();
        p.setNom("Maria");
        p.setCognoms("Cognoms Maria");
        session.save(p);

        session.getTransaction().commit();
        session.close();

        System.out.println("Persona guardada correctament.");
    }
}
