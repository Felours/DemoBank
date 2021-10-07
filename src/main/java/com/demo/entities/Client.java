package com.demo.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Client implements Serializable {

    @Id @GeneratedValue
    private Long code;
    private String nom;
    private String prenom;
    private String email;

    @OneToMany(mappedBy="client", fetch= FetchType.LAZY)
    private Collection<Compte> comptes;

    public Client(){

        this.code = null;
        this.nom = null;
        this.prenom = null;
        this.email = null;

    }

    public Client(String nom, String prenom, String email){

        this.nom = nom;
        this.prenom = prenom;
        this.email = email;

    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(Collection<Compte> comptes) {
        this.comptes = comptes;
    }

    //@Transient
    public String getFullName(){

        StringBuilder sb = new StringBuilder();
        sb.append(this.prenom);
        sb.append(" ");
        sb.append(this.nom);

        return sb.toString();

    }

    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();
        sb.append("Client[");

        sb.append("code: ");
        sb.append(this.getCode());

        sb.append(", nom: ");
        sb.append(this.getNom());

        sb.append(", prenom: ");
        sb.append(this.getPrenom());

        sb.append(", email: ");
        sb.append(this.getEmail());

        sb.append("]");

        return sb.toString();

    }

}
