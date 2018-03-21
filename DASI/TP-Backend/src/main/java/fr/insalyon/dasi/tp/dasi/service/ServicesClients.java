/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.tp.dasi.service;

import fr.insalyon.dasi.tp.dasi.dao.*;
import fr.insalyon.dasi.tp.dasi.model.Client;
import fr.insalyon.dasi.tp.dasi.model.Conversation;
import fr.insalyon.dasi.tp.dasi.model.Employe;
import fr.insalyon.dasi.tp.dasi.model.Medium;
import fr.insalyon.dasi.tp.dasi.util.AstroTest;
import java.io.IOException;
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
            String mail = recupererMailAEnvoyer(false, c);
            System.out.println(mail);
            return false;
        }
        
        
        
        try { //si cette adresse email existe déjà, le client n'est pas créé
            d.getClient(c.getEmail());
            JpaUtil.annulerTransaction();
            JpaUtil.fermerEntityManager();
            String mail = recupererMailAEnvoyer(false, c);
            System.out.println(mail);
            return false;
        }
        catch (Exception e) { //on crée le client si on ne le trouve pas
            try {
                d.persist(c);
                JpaUtil.validerTransaction();
                String mail = recupererMailAEnvoyer(true, c);
                System.out.println(mail);
                JpaUtil.fermerEntityManager();
                return true;
            } catch (Exception ee) {
                JpaUtil.annulerTransaction();
                JpaUtil.fermerEntityManager();
                String mail = recupererMailAEnvoyer(false, c);
                System.out.println(mail);
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
    
    public boolean creerConversation(Client c, Medium m) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        DAOConversation dc = new DAOConversation();
        DAOEmploye daoEmp = new DAOEmploye();
        DAOMedium daoMed = new DAOMedium();
        DAOClient daoClient = new DAOClient();
        
        Conversation conv = new Conversation();
        conv.setClient(c);
        conv.setMedium(m);
        conv.setDateDebut(null);
        conv.setDateFin(null);
        
       
        Employe emp = daoEmp.getEmployeToAffect(m);
        if(emp==null)   // aucun employé disponible
            return false;
        
        
        boolean success = true;
        try {
            // on indique que l'employé n'est plus disponible
            emp.setDisponible(false);
            daoEmp.merge(emp);
            
            // on persiste la conversation
            conv.setEmploye(emp);
            dc.persist(conv);
            
            // on met à jour employé, medium et client
            emp.addConversation(conv);
            m.addConversation(conv);
            c.addConversation(conv);
            
            daoEmp.merge(emp);
            daoClient.merge(c);
            daoMed.merge(m);
            
            JpaUtil.validerTransaction();
            
            String notification = recupererNotificationAEnvoyer(conv);
            System.out.println(notification);
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
    
    private String recupererMailAEnvoyer(boolean inscriptionReussie, Client c) {
        String mail = "\033[4mExpediteur\033[0m : contact@posit.if\n"
                +     "\033[4mPour\033[0m : " + c.getEmail()+"\n"
                +     "\033[4mSujet\033[0m : Bienvenue chez POSIT'IF\n";
        
        if (inscriptionReussie)
            mail  +=  "\033[4mCorps\033[0m :\n"
                  +   "Bonjour "+c.getPrenom()+",\n"
                  +   "Nous vous confirmons votre inscription au service POSIT'IF. Votre numéro client est : "+c.getId() +".";
        else
            mail  +=  "\033[4mCorps\033[0m :\n"
                  +   "Bonjour "+c.getPrenom()+",\n"
                  +   "Votre inscription au service POSIT'IF a malencontrueusement échoué... Merci de recommencer ultérieurement.";
    
        return mail;
    }
    
    public String recupererNotificationAEnvoyer(Conversation conv){
        String message = "Voyance demandée pour client "+conv.getClient().getPrenom() + " "
                        + conv.getClient().getPrenom() + "(#" + conv.getClient().getId() + "), Médium : "
                        + conv.getMedium().getNom();
        return message;
    }
    
    
    
}
