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
public class Voyant extends Medium implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String support;

    
    public Voyant() {
        super("","","");
    }
    public Voyant(String nom, String bio, String talent, String support) {
        super(nom, bio, talent);
        this.support = support;
    }
    
    
    
    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public Long getId() {
        return id;
    }

}
