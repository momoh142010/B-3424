/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.tp.dasi.service;

import fr.insalyon.dasi.tp.dasi.dao.DAOClient;
import fr.insalyon.dasi.tp.dasi.dao.DAOConversation;
import fr.insalyon.dasi.tp.dasi.dao.DAOEmploye;
import fr.insalyon.dasi.tp.dasi.dao.DAOMedium;
import fr.insalyon.dasi.tp.dasi.dao.JpaUtil;
import fr.insalyon.dasi.tp.dasi.model.Astrologue;
import fr.insalyon.dasi.tp.dasi.model.Client;
import fr.insalyon.dasi.tp.dasi.model.Conversation;
import fr.insalyon.dasi.tp.dasi.model.Employe;
import fr.insalyon.dasi.tp.dasi.model.Medium;
import fr.insalyon.dasi.tp.dasi.model.Tarologue;
import fr.insalyon.dasi.tp.dasi.model.Voyant;
import fr.insalyon.dasi.tp.dasi.util.AstroTest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;

/**
 *
 * @author mchallal
 */
public class ServicesEmployes {
    

    public ServicesEmployes() {
        
    }
    
    // renvoie l'employe avec les identifiants passés en paramètre
    // renvoie null si aucun employé ne correspond aux identifiants
    public Employe connecter(String email, String password){
        JpaUtil.creerEntityManager();
        DAOEmploye daoEmploye = new DAOEmploye();
        Employe emp = null;
        
        try{
            emp = daoEmploye.getEmploye(email, password);
        
        }catch(NoResultException exp){
            System.out.println("les identifiants sont incorrects !");
            JpaUtil.fermerEntityManager();
            return null;
        }
        
        JpaUtil.fermerEntityManager();
        return emp;
    }
    
    // persiste l'employé dans la base de données
    // TODO : verifier que l'employe n'existe pas déjà
    public boolean inscrire(Employe emp){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        DAOEmploye daoEmploye = new DAOEmploye();
        
        /*
        daoEmploye.persist(e);
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        */
        
        
        try { //si cette adresse email existe déjà, le client n'est pas créé
            daoEmploye.getEmploye(emp.getEmail());
            JpaUtil.annulerTransaction();
            JpaUtil.fermerEntityManager();
            return false;
        }
        catch (Exception e) { //on crée le client si on ne le trouve pas
            try {
                daoEmploye.persist(emp);
                JpaUtil.validerTransaction();
                JpaUtil.fermerEntityManager();
                return true;
            } catch (Exception ee) {
                JpaUtil.annulerTransaction();
                JpaUtil.fermerEntityManager();
                return false;
            }
        }
    }
    
    
    
    
    
    
    public void creerMedium(Medium m){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        DAOMedium daoMedium = new DAOMedium();
        daoMedium.persist(m);
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }
    
    // TODO : à débuguer !
    // nbre de voyances demandées pour chaque medium
    public Map<String, Long> recupererStats1(){
        Map<String, Long> map =  new HashMap<>() ;
        
        JpaUtil.creerEntityManager();
        DAOMedium dm = new DAOMedium();
        
        List<Medium> mediums = dm.getAll();
        
        
        for (int i = 0; i < mediums.size(); i++) {
            String nom = mediums.get(i).getNom();
            Long nbDemandes = dm.getNbVoyancesDemandees(mediums.get(i));
            //System.out.println(nbDemandes);
            map.put(nom, nbDemandes);
        }
        
        JpaUtil.fermerEntityManager();
        
        /*Iterator i = map.keySet().iterator();
        while (i.hasNext())
        {
            Long valeur = (Long)map.get((String)i.next());
            System.out.println(valeur);
        }
        System.out.println(map);
        */
        
        
        
        return map;
    }
    
    // retourne le total de voyances réalisées par employé
    public Map<String, Integer> recupererStats2(){
        Map<String, Integer> map =  new HashMap<String, Integer>() ;
        
        JpaUtil.creerEntityManager();
        DAOEmploye daoEmploye = new DAOEmploye();
        
        List<Employe> employes = daoEmploye.getAll();
        JpaUtil.fermerEntityManager();
        
        for (int i = 0; i < employes.size(); i++) {
            String nom = employes.get(i).getPrenom() + " " + employes.get(i).getNom();
            Integer nbVoyances = employes.get(i).getTotalAffectations();
            map.put(nom, nbVoyances);
        }
        
        return map;
    }
    
    // retourne la répartition des voyances entre employés 
    public Map<String, Double> recupererStats3(){
        Map<String, Double> map =  new HashMap<String, Double>() ;
        
        JpaUtil.creerEntityManager();
        DAOEmploye daoEmploye = new DAOEmploye();
        List<Employe> employes = daoEmploye.getAll();
        
        Long somme= daoEmploye.getTotalAffectations();
        JpaUtil.fermerEntityManager();
        
        for (int i = 0; i < employes.size(); i++) {
            String nom = employes.get(i).getPrenom() + " " + employes.get(i).getNom();
            Double nbVoyances = Double.valueOf(employes.get(i).getTotalAffectations());
            if(somme==0)
                map.put(nom, 0.0);
            else
                map.put(nom, nbVoyances/somme);
        }
        
        return map;
    }
    
    
    public Conversation recupererDemandeDeConversations(Employe e){
        JpaUtil.creerEntityManager();
        
        DAOConversation daoConv = new DAOConversation();
        Conversation conv = daoConv.getDemandesDeConversations(e);
        
        JpaUtil.fermerEntityManager();
        return conv;
    }
    
    
    public void commencerConversation(Conversation c){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        DAOConversation daoConv = new DAOConversation();
        c.setDateDebut(new Date());
        daoConv.merge(c);
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }
    
    
    
    public void finirConversation(Conversation c, String Commentaire){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        DAOConversation daoConv = new DAOConversation();
        c.setDateFin(new Date());
        c.setCommentaire(Commentaire);
        daoConv.merge(c);
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }
    public void finirConversation(Conversation c){
        finirConversation(c, "");
    }
    
    
    
    void initialiser(){
        Employe e1 = new Employe("GIREUX", "Zouhair", "0123456789", "zouhair.gireux@posit.if", "jaguar" , 8);
        Employe e2 = new Employe("TCHIUMAKOVA", "Nicolas", "095126874", "nicolas.tchiumakova@posit.if", "loutre", 17);
        Employe e3 = new Employe("KEMARO", "Cédric", "0573268941", "cedric.kemaro@posit.if", "dauphin",3);
        Employe e4 = new Employe("PAMITESCU", "Olena", "0253641789", "olena.pamitescu@posit.fr", "moustique", 12);
        Employe e5 = new Employe("FER", "Lucie", "0578426931", "lucie.fer@posit.fr", "signe", 19);
        Employe e6 = new Employe("BOU", "Marah", "0628539741", "marah.bou@posit.fr", "tigre", 7);
        
        
        
        Medium m1 = new Voyant("Gwenaël", "Spécialiste des grandes conversation au-delà de TOUTES les frontières.", "Voyant", "Boule de cristal");
        Medium m2 = new Voyant("J. Dalmarre", "Votre avenir est devant vous: regardons-le ensemble!", "Voyant", "Marc de café");
        Medium m3 = new Tarologue("Mme Irma", "Comprenez votre entourage grâce à mes cartes! Résultats rapides.", "Tarologue", "Tarot de Marseille");
        Medium m4 = new Tarologue("Mme Lisa Maria NGUYINIA", "Mes cartes spécialisées pour la région Bretagne répondront à toutes vos questions personnelles.", "Tarologue", "Tarot de Brocéliande");
        Medium m5 = new Astrologue("Mme Sara", "Basée à Champigny-sur-Marne, Mme Sara vous révèlera votre avenir pour éclairer votre passé.", "Astrologue", "École Normale Supérieure d'Astrologie(ENS-Astro)", "2006");
        Medium m6 = new Astrologue("Mme Mounia Mounia", "Avenir, avenir, que nous réserves-tu? N'attendez plus, demandez à me consulter!", "Astrologue", "Institut des Nouveaux Savoirs Astrologiques", "2010");
        
        
        
        creerMedium(m1);
        creerMedium(m2);
        creerMedium(m3);
        creerMedium(m4);
        creerMedium(m5);
        creerMedium(m6);
        
        e1.addMedium(m1);
        e1.addMedium(m3);
        e2.addMedium(m6);
        e4.addMedium(m1);
        e3.addMedium(m3);
        e5.addMedium(m3);
        
        
        
        
        inscrire(e1);
        inscrire(e2);
        inscrire(e3);
        inscrire(e4);
        inscrire(e5);
        inscrire(e6);
        
        m1.addEmploye(e3);
        m1.addEmploye(e4);
        m2.addEmploye(e3);
        m3.addEmploye(e3);
        m4.addEmploye(e3);
        m5.addEmploye(e3);
        m6.addEmploye(e3);
        
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        DAOMedium dao = new DAOMedium();
        dao.merge(m1);
        dao.merge(m2);
        dao.merge(m3);
        dao.merge(m4);
        dao.merge(m5);
        dao.merge(m6);
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        
        
        /*
        JpaUtil.creerEntityManager();
        DAOEmploye d = new DAOEmploye();
        Employe e = d.getEmployeToAffect(m1);
        System.out.println("les employes : ");
        System.out.println(e);
        JpaUtil.fermerEntityManager();
        */
        
        
    }
    
    
    
    public static void main(String args[]) {
        JpaUtil.init();
        
        ServicesEmployes SE = new ServicesEmployes();
        SE.initialiser();
        
        Employe e=SE.connecter("zouhair.gireux@posit.if", "jaguar");
        DAOMedium dm = new DAOMedium();
        DAOEmploye de = new DAOEmploye();
        Medium m = dm.getByName("Gwenaël").get(0);
        System.out.println(e);
        System.out.println(m);
        
        e.addMedium(m);
        m.addEmploye(e);

        dm.persist(m);
        de.persist(e);
        
        System.out.println("ICI : "+SE.connecter("zouhair.gireux@posit.if", "jaguar").getMediums().toString());
        
        //JpaUtil.creerEntityManager();
        //DAOEmploye d = new DAOEmploye();
        /*
        System.out.println(" ********** deb ...");
        Employe e=SE.connecter("zouhair.gireux@posit.if", "jaguar");
        System.out.println(e);
        System.out.println(" ********** fin ...");*/
        //JpaUtil.fermerEntityManager();
        
        JpaUtil.destroy();
        
    }
    
    
    
}
