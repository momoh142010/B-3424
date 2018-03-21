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
 * @author mchallal
 */
public class DAOEmploye {
    
    public DAOEmploye(){    }
    
    public Employe getEmploye(String email, String password) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query q = em.createQuery("select e from Employe e where e.email = :email and e.password = :password");
        q.setParameter("email", email).setParameter("password", password);
        Object o = q.getSingleResult();
        return (Employe) o;
    }
    
    public Employe getEmploye(String email) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query q = em.createQuery("select e from Employe e where e.email = :email");
        q.setParameter("email", email);
        Object o = q.getSingleResult();
        return (Employe) o;
    }
    
    
   
    public void persist(Employe e){
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(e);
    }
    
    public void merge(Employe e){
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(e);
    }
    
    public List<Employe> getAll() {
        EntityManager em = JpaUtil.obtenirEntityManager();
        return em.createQuery("select e from Employe e").getResultList();
    }
    
    public List<Employe> getEmployeByMedium(Medium m){
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query q = em.createQuery("select e from Employe e where (:m IN e.mediums)");
        return q.setParameter("m", m).getResultList();
    }
    
    public Employe getEmployeToAffect(Medium m){
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query q = em.createQuery("SELECT e "
                               + "FROM Employe e "
                               + "WHERE e.disponible = TRUE "
                               + "ORDER BY e.totalAffectations ASC ");
        for(Object emp: q.getResultList()){
            if(((Employe)emp).getMediums().contains(m)){
                return (Employe)emp;
            }
        }
        return null;
    }
    
    
    public Long getTotalAffectations(){
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query q = em.createQuery("SELECT sum(e.totalAffectations) FROM Employe e");
        
        return (Long) q.getSingleResult();
    }
    
}
