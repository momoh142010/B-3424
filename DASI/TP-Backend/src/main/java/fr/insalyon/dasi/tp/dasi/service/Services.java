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
    
    public Services(){
        
    }
    
    public static void main(String args[]){
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
    
    
}
