/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.tp.dasi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;


@Entity
public class Client implements Serializable {
    
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nom;
    private String prenom;
    private String civilite;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeNaissance ;
    private String adresse;
    private String numTel;
    private String email;
    private String password;
    private String signeZodiaque;
    private String signeChinois;
    private String couleur;
    private String animal;
    
    @OneToMany(mappedBy="client")
    protected List<Conversation> conversations;

    
    
    
    public Client() {    }
    
    public Client(String nom, String prenom, String civilite) {
        this.nom = nom;
        this.prenom = prenom;
        this.civilite = civilite;
        this.conversations = new ArrayList<>();
    }

    public Client(String nom, String prenom, String civilite, Date dateDeNaissance, String adresse, String numTel, String email, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.civilite = civilite;
        this.dateDeNaissance = dateDeNaissance;
        this.adresse = adresse;
        this.numTel = numTel;
        this.email = email;
        this.password = password;
        this.conversations = new ArrayList<>();
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

    public String getCivilite() {
        return civilite;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public String getAdresse() {
        return adresse;
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

    public String getSigneZodiaque() {
        return signeZodiaque;
    }

    public String getSigneChinois() {
        return signeChinois;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getAnimal() {
        return animal;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    
    
    
    
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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

    public void setSigneZodiaque(String sZodiac) {
        this.signeZodiaque = sZodiac;
    }

    public void setSigneChinois(String sAstro) {
        this.signeChinois = sAstro;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }
    
    public void addConversation(Conversation c) {
        this.conversations.add(c);
    }

    public void removeConversation(Conversation c) {
        this.conversations.remove(c);
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
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Client [" + id + "] "+nom+" "+prenom+"\n";
    }
    
}
