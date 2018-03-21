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
public class Astrologue extends Medium implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ecole;
    private String promotion;
    
    public Astrologue() { super("","","");}
    public Astrologue(String nom, String bio, String talent, String ecole, String promotion) {
        super(nom, bio, talent);
        this.ecole = ecole;
        this.promotion = promotion;
    }
    
    
    
    
    public Long getId() {
        return id;
    }
    
    
    public String getEcole() {
        return ecole;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setEcole(String ecole) {
        this.ecole = ecole;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }
    
    
}
