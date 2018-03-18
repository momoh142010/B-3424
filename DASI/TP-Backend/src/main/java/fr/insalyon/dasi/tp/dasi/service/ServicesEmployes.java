/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.tp.dasi.service;

import fr.insalyon.dasi.tp.dasi.dao.DAOClient;
import fr.insalyon.dasi.tp.dasi.dao.DAOEmploye;
import fr.insalyon.dasi.tp.dasi.dao.JpaUtil;
import fr.insalyon.dasi.tp.dasi.model.Client;
import fr.insalyon.dasi.tp.dasi.model.Employe;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

/**
 *
 * @author mchallal
 */
public class ServicesEmployes {
    

    public ServicesEmployes() {
        
    }
    
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
    
    public void inscrire(Employe e){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        DAOEmploye daoEmploye = new DAOEmploye();
        daoEmploye.persist(e);
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }
    
    public Map<String, Integer> getStats(){
        Map<String, Integer> map =  new HashMap<String, Integer>() ;
        
        JpaUtil.creerEntityManager();
        DAOEmploye daoEmploye = new DAOEmploye();
        
        List<Employe> employes = daoEmploye.getAll();
        JpaUtil.fermerEntityManager();
        
        for (int i = 0; i < employes.size(); i++) {
            String nom = employes.get(i).getNom();
            Integer nbVoyances = employes.get(i).getTotalAffectations();
            map.put(nom, nbVoyances);
        }
        
        return map;
    }
    
    public Map<String, Double> getStats2(){
        Map<String, Double> map =  new HashMap<String, Double>() ;
        
        JpaUtil.creerEntityManager();
        DAOEmploye daoEmploye = new DAOEmploye();
        List<Employe> employes = daoEmploye.getAll();
        JpaUtil.fermerEntityManager();
        
        Long somme= daoEmploye.getTotalAffectations();
        for (int i = 0; i < employes.size(); i++) {
            String nom = employes.get(i).getNom();
            Double nbVoyances = Double.valueOf(employes.get(i).getTotalAffectations());
            if(somme==0)
                map.put(nom, 0.0);
            else
                map.put(nom, nbVoyances/somme);
        }
        
        return map;
    }
    
    
    void initialiserLesEmployes(){
        inscrire(new Employe("GIREUX", "Zouhair", "0123456789", "zouhair.gireux@posit.if", "jaguar" , 8));
        inscrire(new Employe("TCHIUMAKOVA", "Nicolas", "095126874", "nicolas.tchiumakova@posit.if", "loutre", 17));
        inscrire(new Employe("KEMARO", "Cédric", "0573268941", "cedric.kemaro@posit.if", "dauphin",3));
        inscrire(new Employe("PAMITESCU", "Olena", "0253641789", "olena.pamitescu@posit.fr", "moustique", 12));
    }
    
    
    
    public static void main(String args[]) {
        JpaUtil.init();
        
        ServicesEmployes SE = new ServicesEmployes();
        SE.initialiserLesEmployes();
        
        //JpaUtil.creerEntityManager();
        
        //DAOEmploye d = new DAOEmploye();
        
        System.out.println(" ********** deb ...");
        
        
        Employe e=SE.connecter("zouhair.gireux@posit.if", "jaguar0");
        if(e!=null)
            System.out.println("introuvable !");
        else
            System.out.println(e);
        
        System.out.println(" ********** fin ...");
        //JpaUtil.fermerEntityManager();
        
        JpaUtil.destroy();
        
        System.out.println(" ********** testing ...");
    }
    
    
    
}
