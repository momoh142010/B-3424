/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.tp.dasi.service;

import fr.insalyon.dasi.tp.dasi.dao.DAOConversation;
import fr.insalyon.dasi.tp.dasi.dao.DAOEmploye;
import fr.insalyon.dasi.tp.dasi.dao.DAOMedium;
import fr.insalyon.dasi.tp.dasi.dao.JpaUtil;
import fr.insalyon.dasi.tp.dasi.model.Client;
import fr.insalyon.dasi.tp.dasi.model.Conversation;
import fr.insalyon.dasi.tp.dasi.model.Employe;
import fr.insalyon.dasi.tp.dasi.model.Medium;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author edelahaye
 */
public class Services {
    
    public Services(){  }
    
    
    static void grosTest(){
        JpaUtil.init();
        
        ServicesEmployes se = new ServicesEmployes();
        ServicesClients  sc = new ServicesClients();
        
        se.initialiser();
        
        Employe e = se.connecter("zouhair.gireux@posit.if", "jaguar");
        System.out.println(e.getMediums().size());
        Medium m = sc.recupererListeMediums().get(0);
        System.out.println(m.getEmployes().size());
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = sdf.parse("21/12/2012");
        } catch (ParseException ex) {
            Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
        }
        Client c1 = new Client("Roger", "Basile", "M.", d, null, null, "aa@a.fr", "123456");
        sc.inscrire(c1);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	Date date = new Date();
	System.out.println(dateFormat.format(date));
        
        
        
        JpaUtil.destroy();
    }
    
    
    static void faireUneDemande(){
        
        ServicesEmployes se = new ServicesEmployes();
        ServicesClients  sc = new ServicesClients();
        
        se.initialiser();
        
        
        //! connexion avec un client qui n'existe pas !
        Client client1 = sc.connecter("123@45.67", "00200");
        if(client1==null)
            System.out.println("Désolé vous n'etes pas inscrit");
        System.out.println("************************************************");
        
        
        //! inscription d'un client AFFRITT Barack
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date2 = null;
        try {
            date2 = sdf.parse("21/12/2012");
        } catch (ParseException ex) {
            Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
        }
        Client client2 = new Client("Barack", "AFFRITT", "M.", date2, "14 rue du McDO", "0352694817", "baffritt@posit.fr", "BurgerKingForever");
        boolean response2 = sc.inscrire(client2);
        if(response2)
            System.out.println("Vous etes bien inscrit ! :)");
        else
            System.out.println("Un problème est survenu ! :(");
        System.out.println("************************************************");
        
        
        //! AFFRITT Barack aime tellement le site qu'il veut se réinscrire ...
        boolean response3 = sc.inscrire(client2);
        if(response3)
            System.out.println("Vous etes bien inscrit ! :)");
        else
            System.out.println("Un problème est survenu ! :(");
        System.out.println("************************************************");
        
        
        //! Barack revient le lendemain pour se connecter et tester cette nouvelle pépite du web qu'il vient de trouver
        Client client4 = sc.connecter("baffritt@posit.fr", "BurgerKingForever");
        System.out.println(client4);
        System.out.println(sc.recupererListeMediums());
        System.out.println("************************************************");
        
        
        //! Barack revient le lendemain pour se connecter et tester cette nouvelle pépite du web qu'il vient de trouver
        Medium medium5 = sc.recupererListeMediums().get(2);
        System.out.println(medium5);
        sc.creerConversation(client4, medium5);
        System.out.println("************************************************");
        
        
    }
    
    
    static void accepterDemande(){
        
        
        ServicesEmployes se = new ServicesEmployes();
        ServicesClients  sc = new ServicesClients();
        
        se.initialiser();
        
        // les stats
        System.out.println(se.recupererStats1());
        System.out.println(se.recupererStats2());
        System.out.println(se.recupererStats3());
        System.out.println("************************************************");
        
        
        // le nouvel employé zouhair veut accéder à sa session
        Employe employe1 = se.connecter("zouhair.gireux@posit.if", "jaguar");
        Employe employe2 = se.connecter("je.n@existe.pas", "mdp");
        if(employe1==null)
            System.out.println("mauvais identifiants pour zouhair.gireux@posit.if ou le compte n'existe pas");
        else
            System.out.println(employe1);
        if(employe2==null)
            System.out.println("mauvais identifiants pour je.n@existe.pas ou le compte n'existe pas");
        else
            System.out.println(employe2);
        System.out.println("************************************************");
        
        
        // zouhair veut créer un 2ème compte pour gagner 2 fois plus d'argent !!
        Employe employe3 = new Employe("zouhair", "gireux", "0123654789", "zouhair.gireux@posit.if", "panthere", 0);
        boolean ok = se.inscrire(employe3);
        if(ok)
            System.out.println("vous etes bien inscrit !");
        else
            System.out.println("Un problème est survenu lors de l'inscription !");
        System.out.println("************************************************");
        
        
        // Cedric a reçu une notification !
        //Employe employe4 = se.connecter("cedric.kemaro@posit.if", "dauphin");
        
        
        
        
        
        
    }
    
    
    public static void main(String[] args){
        JpaUtil.init();
        
        faireUneDemande();
        accepterDemande();
        
        JpaUtil.destroy();
    }
    
    
}
