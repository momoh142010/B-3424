/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.tp.dasi.service;

import fr.insalyon.dasi.tp.dasi.dao.*;
import fr.insalyon.dasi.tp.dasi.model.Astrologue;
import fr.insalyon.dasi.tp.dasi.model.Client;
import fr.insalyon.dasi.tp.dasi.model.Conversation;
import fr.insalyon.dasi.tp.dasi.model.Employe;
import fr.insalyon.dasi.tp.dasi.model.Medium;
import fr.insalyon.dasi.tp.dasi.model.Tarologue;
import fr.insalyon.dasi.tp.dasi.model.Voyant;
import fr.insalyon.dasi.tp.dasi.util.AstroTest;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author edelahaye
 */
public class ServicesClients {
    

    public ServicesClients() {    }
    
    public boolean inscrire(Client c) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        DAOClient d = new DAOClient();
        
        try {   // on récupère le profil astro du client
            // une clé par défaut de l'api est mise directement dans le constructeur
            AstroTest generator = new AstroTest();  
        
            List profil = generator.getProfil(c.getPrenom(), c.getDateDeNaissance());
            c.setSigneZodiaque((String) profil.get(0));
            c.setSigneChinois((String) profil.get(1));
            c.setCouleur((String) profil.get(2));
            c.setAnimal((String) profil.get(3));
        } catch (IOException ex) {
            Logger.getLogger(ServicesClients.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("l'application n'a pas réussi à générer un profil astro au client !");
            
            JpaUtil.annulerTransaction();
            JpaUtil.fermerEntityManager();
            envoyerMail(false, c);
            return false;
        }
        
        
        
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
        DAOClient d = new DAOClient();
        
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
        JpaUtil.creerEntityManager();
        DAOMedium dm = new DAOMedium();
        List<Medium> liste = null;
        
        try {
            liste = dm.getAll();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        JpaUtil.fermerEntityManager();
        return liste;
    }
    
    //TODO : Récupérer l'employé à affecter à la conversation
    public boolean creerConversation(Client c, Medium m) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        DAOConversation dc = new DAOConversation();
        
        Conversation conv = new Conversation();
        conv.setClient(c);
        conv.setMedium(m);
        conv.setDateDebut(null);
        conv.setDateFin(null);
        
        DAOEmploye daoEmp = new DAOEmploye();
        Employe emp = daoEmp.getEmployeToAffect(m);
        if(emp==null)   // aucun employé disponible
            return false;
        emp.setDisponible(false);
        daoEmp.merge(emp);
        conv.setEmploye(emp);
        
        boolean success = true;
        try {
            dc.persist(conv);
            JpaUtil.validerTransaction();
        }
        catch (Exception e) {
            System.err.println("Error while creating a conversation");
            JpaUtil.annulerTransaction();
            success = false;
        }
        finally {
            JpaUtil.fermerEntityManager();
        }
        
        return success;
    }
    
    
    
    
    private void envoyerMail(boolean inscriptionReussie, Client c) {
        System.out.println("\033[4mExpediteur\033[0m : contact@posit.if\n"
                +          "\033[4mPour\033[0m : " + c.getEmail()+"\n"
                +          "\033[4mSujet\033[0m : Bienvenue chez POSIT'IF\n");
        if (inscriptionReussie)
            System.out.println("\033[4mCorps\033[0m :\n"
                +           "Bonjour "+c.getPrenom()+",\n"
                +           "Nous vous confirmons votre inscription au service POSIT'IF. Votre numéro client est : "+c.getId() +".");
        else
            System.out.println("\033[4mCorps\033[0m :\n"
                +           "Bonjour "+c.getPrenom()+",\n"
                +           "Votre inscription au service POSIT'IF a malencontrueusement échoué... Merci de recommencer ultérieurement.");
    }
    
    
}
