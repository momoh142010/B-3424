/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.tp.dasi.service;

import fr.insalyon.dasi.tp.dasi.dao.*;
import fr.insalyon.dasi.tp.dasi.model.Client;
import fr.insalyon.dasi.tp.dasi.model.Conversation;
import fr.insalyon.dasi.tp.dasi.model.Medium;
import java.util.Date;
import java.util.List;

/**
 *
 * @author edelahaye
 */
public class ServicesClients {
    
    DAOClient d;
    DAOMedium dm;
    DAOConversation dc;
    public ServicesClients() {
        d = new DAOClient();
        dm = new DAOMedium();
        dc = new DAOConversation();
    }
    
    public boolean inscrire(Client c) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        try { //si cette adresse email existe déjà, le client n'est pas créé
            d.getClient(c.getEmail());
            JpaUtil.annulerTransaction();
            JpaUtil.fermerEntityManager();
            envoyerMail(false, c);
            return false;
        }
        catch (Exception e) { //on crée le client si on ne le trouve pas
            try {
                d.persist(c);
                JpaUtil.validerTransaction();
                envoyerMail(true, c);
                JpaUtil.fermerEntityManager();
                return true;
            } catch (Exception ee) {
                JpaUtil.annulerTransaction();
                JpaUtil.fermerEntityManager();
                envoyerMail(false, c);
                return false;
            }
        }
    }

    public Client connecter(String email, String password) {
        JpaUtil.creerEntityManager();
        try {
            Client c = d.getClient(email, password);
            System.out.println("CONNECTED "+email+" "+password);
            return c;
        } catch (Exception e) {
            System.out.println("FAIL TO CONNECT "+email+" "+password);
            return null;
        }
    }
    
    public List<Medium> recupererListeMediums() {
        try {
            return dm.getAll();
        }
        catch(Exception e) {
            return null;
        }
    }
    
    //TODO : Récupérer l'employé à affecter à la conversation
    public void commencerConversation(Client c, Medium m) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        Conversation conv = new Conversation();
        conv.setClient(c);
        conv.setMedium(m);
        conv.setDateDebut(null);
        conv.setDateFin(null);
        conv.setEmploye(null);
        
        try {
            dc.persist(conv);
            JpaUtil.validerTransaction();
        }
        catch (Exception e) {
            System.err.println("Error while creating a conversation");
            JpaUtil.annulerTransaction();
        }
        finally {
            JpaUtil.fermerEntityManager();
        }
    }
    
    
    
    private void envoyerMail(boolean b, Client c) {
        System.out.println("\033[4mExpediteur\033[0m : contact@posit.if\n"
                +          "\033[4mPour\033[0m : " + c.getEmail()+"\n"
                +          "\033[4mSujet\033[0m : Bienvenue chez POSIT'IF\n");
        if (b)
            System.out.println("\033[4mCorps\033[0m :\n"
                +           "Bonjour "+c.getPrenom()+",\n"
                +           "Nous vous confirmons votre inscription au service POSIT'IF. Votre numéro client est : "+c.getId() +".");
        else
            System.out.println("\033[4mCorps\033[0m :\n"
                +           "Bonjour "+c.getPrenom()+",\n"
                +           "Votre inscription au service POSIT'IF a malencontrueusement échoué... Merci de recommencer ultérieurement.");
    }
}
