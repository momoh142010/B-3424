/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.tp.dasi.dao;

import fr.insalyon.dasi.tp.dasi.model.Client;
import fr.insalyon.dasi.tp.dasi.model.Conversation;
import fr.insalyon.dasi.tp.dasi.model.Employe;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author mchallal
 */
public class DAOConversation {

    public DAOConversation() {
    }
    
    public void persist(Conversation c){
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(c);
    }
    
    
    
}
