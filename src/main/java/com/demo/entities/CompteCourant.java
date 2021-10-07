package com.demo.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("CC")
public class CompteCourant extends Compte {

    private Double decouvert;

    public CompteCourant(){

        super();
        this.decouvert = 0.0;

    }

    public CompteCourant(String codeCompte, Date dateCreation, Double solde, Client client, Double decouvert) {

        super(codeCompte, dateCreation, solde, client);
        this.decouvert = decouvert;

    }

    public Double getDecouvert() {
        return decouvert;
    }

    public void setDecouvert(Double decouvert) {
        this.decouvert = decouvert;
    }

    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();
        sb.append("CompteCourant[");

        sb.append(super.buildCoreToString());

        sb.append(", decouvert: ");
        sb.append(this.getDecouvert());

        sb.append("]");

        return sb.toString();

    }

}
