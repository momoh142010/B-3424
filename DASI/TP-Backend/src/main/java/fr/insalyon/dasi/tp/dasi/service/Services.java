/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.tp.dasi.service;

import fr.insalyon.dasi.tp.dasi.dao.JpaUtil;
import fr.insalyon.dasi.tp.dasi.model.Client;

/**
 *
 * @author edelahaye
 */
public class Services {
    
    public Services(){
        
    }
    
      public static void main(String args[]) {
        JpaUtil.init();
        ServicesClients sc = new ServicesClients();
        ServicesEmployes se = new ServicesEmployes();
        
        Client c = new Client("Van beurrrrrdeeeeeune", "Alex", null, null, null, null, "jb@a.com", "password");
        sc.inscrire(c);
        sc.inscrire(c);
        
        sc.connecter("a", "b");
        sc.connecter("jb@a.com", "password");
        
        JpaUtil.destroy();
    }
    
    
}
