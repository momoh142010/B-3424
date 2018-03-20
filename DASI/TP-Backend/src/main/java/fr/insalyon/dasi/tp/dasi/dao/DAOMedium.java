/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.tp.dasi.dao;

import fr.insalyon.dasi.tp.dasi.model.Employe;
import fr.insalyon.dasi.tp.dasi.model.Medium;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;


/**
 *
 * @author edelahaye
 */
public class DAOMedium {
    
    public DAOMedium() {
    }
    
    public List<Medium> getAll() {
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        return em.createQuery("select m from Medium m").getResultList();
    }
    
    public List<Medium> getByName(String name) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        return em.createQuery("select c from Medium c where c.nom = :name").setParameter("name", name).getResultList();
    }
    
    public List<Medium> getByTalent(String talent) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        return em.createQuery("select c from Medium c where c.nom = :tal").setParameter("tal", talent).getResultList();
    }
   
    public void persist(Medium c){
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(c);
    }
    
    public void merge(Medium m){
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(m);
    }
    
    // ne fonctionne pas !
    public Long getNbVoyancesDemandees(Medium m){
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        Query query = em.createQuery("SELECT count(c) FROM Conversation c WHERE :m = c.medium");
        query.setParameter("m", m);
        
        long conversationCount = (Long)query.getSingleResult();
        //System.out.println(m.toString()+" "+conversationCount);
        
        return conversationCount;
    }
    
    
    
    
}
