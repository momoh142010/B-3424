/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.tp.dasi.dao;

import fr.insalyon.dasi.tp.dasi.model.Client;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author edelahaye
 */
public class DAOClient {
    
    public DAOClient() {
    }
    
     public List<Client> getAll() {
        EntityManager em = JpaUtil.obtenirEntityManager();
        return em.createQuery("select c from Client c").getResultList();
    }
    
     public Client getClient(String email) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        return (Client) em.createQuery("select c from Client c where c.nom = :email").setParameter("email", email).getSingleResult();
    }
    
    public Client getClient(String email, String password) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Client c = (Client) em.createQuery("select c from Client c where c.email = :email and c.password = :password").setParameter("email", email).setParameter("password", password).getSingleResult();
        Integer foobar = c.getConversations().size(); //POur forcer la méthode à récupérer la liste dixit le prof
        return c;
        
    }
   
    public void persist(Client c) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(c);
    }
    
    public void merge(Client c){
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(c);
    }
    
    
}
