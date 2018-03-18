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
    
    public Astrologue() { }
    public Astrologue(String ecole, String promotion) {
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
    
    

/*
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Astrologue)) {
            return false;
        }
        Astrologue other = (Astrologue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.insalyon.dasi.tp.dasi.model.Astrologue[ id=" + id + " ]";
    }
   */ 

    
    
}
