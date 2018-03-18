/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.tp.dasi.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author edelahaye
 */
@Entity
public class Tarologue extends Medium implements Serializable {
 
    private String cartes;
    
    public Tarologue() {
        super("","","");
    }
    
    public Tarologue(String nom, String bio, String talent, String cartes) {
        super(nom, bio, talent);
        this.cartes = cartes;
    }

    public String getCartes() {
        return cartes;
    }

    public void setCartes(String cartes) {
        this.cartes = cartes;
    }
    
    

}
