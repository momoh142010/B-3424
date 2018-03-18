/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.tp.dasi.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author edelahaye
 */
@Entity
public class Employe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nom;
    private String prenom;
    private String numTel;
    private String email;
    private String password;
    private Integer totalAffectations;
    private Integer hDebut;
    private Integer hFin;
    
    boolean disponible;

    
    @OneToMany(mappedBy="employe")
    protected Collection<Conversation> conversations;
    @ManyToMany(mappedBy="employes")
    protected Collection<Medium> mediums;
    //pas de disponibilité (bool) mais si on n'y arrive pas on le rajoutera

    
    
    public Employe() {}
    
    public Employe(String nom, String prenom, String numTel, Integer totalAffectations) {
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.totalAffectations = totalAffectations;
    }

    public Employe(String nom, String prenom, String numTel, String email, String password, Integer totalAffectations) {
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.email = email;
        this.password = password;
        this.totalAffectations = totalAffectations;
    }

    
    
    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNumTel() {
        return numTel;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getTotalAffectations() {
        return totalAffectations;
    }

    public Integer gethDebut() {
        return hDebut;
    }

    public Integer gethFin() {
        return hFin;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public Collection<Conversation> getConversations() {
        return conversations;
    }

    public Collection<Medium> getMediums() {
        return mediums;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTotalAffectations(Integer totalAffectations) {
        this.totalAffectations = totalAffectations;
    }

    public void sethDebut(Integer hDebut) {
        this.hDebut = hDebut;
    }

    public void sethFin(Integer hFin) {
        this.hFin = hFin;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void addConversation(Conversation c) {
        this.conversations.add(c);
        ++this.totalAffectations;
    }

    public void addMedium(Medium m) {
        this.mediums.add(m);
    }
    
    public void removeConversation(Conversation c) {
        this.conversations.remove(c);
        --this.totalAffectations;
    }

    public void removeMedium(Medium m) {
        this.mediums.remove(m);
    }

    
    
    
  
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employe)) {
            return false;
        }
        Employe other = (Employe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.insalyon.dasi.tp.dasi.model.Employe[ id=" + id + " ]\n"+nom+" "+prenom+"\n";
    }
    
}
