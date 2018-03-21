/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.tp.dasi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 *
 * @author edelahaye
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Medium implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    protected String nom;
    protected String bio;
    protected String talent;
    
    @OneToMany(mappedBy="medium")
    protected List<Conversation> conversations;
    
    @ManyToMany
    protected List<Employe> employes;
    
    @Version
    private Long version;

    
    protected Medium(String nom, String bio, String talent) {
        this.nom = nom;
        this.bio = bio;
        this.talent = talent;
        this.conversations = new ArrayList<>();
        this.employes = new ArrayList<>();
    }
    
    
    
    
    
    

    
    public Long getId() {
        return id;
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getTalent() {
        return talent;
    }

    public void setTalent(String talent) {
        this.talent = talent;
    }
    
    
    
    
    public void addConversation(Conversation c) {
        this.conversations.add(c);
    }

    public void addEmploye(Employe e) {
        this.employes.add(e);
    }
    
    public void removeConversation(Conversation c) {
        this.conversations.remove(c);
    }

    public void removeEmploye(Employe e) {
        this.employes.remove(e);
    }
    
    
    public List<Conversation> getConversations() {
        return conversations;
    }

    public List<Employe> getEmployes() {
        return employes;
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
        if (!(object instanceof Medium)) {
            return false;
        }
        Medium other = (Medium) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }
    

    @Override
    public String toString() {
        return "Medium [" + id + "] "+nom+"\n";
    }
    

    
    
}
