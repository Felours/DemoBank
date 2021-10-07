package com.demo.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("CE")
public class CompteEpargne extends Compte {

    private Double taux;

    public CompteEpargne(){

        super();
        this.taux = 0.0;

    }

    public CompteEpargne(String codeCompte, Date dateCreation, Double solde, Client client, Double taux) {

        super(codeCompte, dateCreation, solde, client);
        this.taux = taux;

    }

    public Double getTaux() {
        return taux;
    }

    public void setTaux(Double taux) {
        this.taux = taux;
    }

    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();
        sb.append("CompteCourant[");

        sb.append(super.buildCoreToString());

        sb.append(", taux: ");
        sb.append(this.getTaux());

        sb.append("]");

        return sb.toString();

    }

}
