package com.demo.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE_OP", discriminatorType = DiscriminatorType.STRING, length = 1)
public abstract class Operation implements Serializable {

    @Id
    @GeneratedValue
    private Long numero;
    private Date dateOperation;
    private Double montant;

    @ManyToOne
    @JoinColumn(name = "CODE_CPTE")
    private Compte compte;

    public Operation() {
    }

    public Operation(Date dateOperation, Double montant, Compte compte) {

        this.dateOperation = dateOperation;
        this.montant = montant;
        this.compte = compte;

    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();
        sb.append("Operation[");

        sb.append("numero: ");
        sb.append(this.getNumero());

        sb.append(", dateOperation: ");
        sb.append(this.getDateOperation());

        sb.append(", montant: ");
        sb.append(this.getMontant());

        //sb.append(", compte: ");
        //sb.append(this.getCompte());

        sb.append("]");

        return sb.toString();

    }

}
