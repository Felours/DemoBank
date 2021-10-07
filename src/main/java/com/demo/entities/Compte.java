package com.demo.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)   // Car c'est un heritage
@DiscriminatorColumn(name="TYPE_CPTE", discriminatorType = DiscriminatorType.STRING, length = 2)    // Car c'est un single table, il faut definir comment identifier les entites filles dans la BD
public abstract class Compte implements Serializable {

    @Id
    private String codeCompte;
    private Date dateCreation;
    private Double solde;

    @ManyToOne
    @JoinColumn(name="CODE_CLI")    // Pour definir le nom de la clef etrangere generee par many to one
    private Client client;

    @OneToMany(mappedBy = "compte")
    private Collection<Operation> operations;

    public Compte() {
    }

    public Compte(String codeCompte, Date dateCreation, Double solde, Client client) {

        this.codeCompte = codeCompte;
        this.dateCreation = dateCreation;
        this.solde = solde;
        this.client = client;

    }

    public String getCodeCompte() {
        return codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Collection<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Collection<Operation> operations) {
        this.operations = operations;
    }

    protected String buildCoreToString(){

        StringBuilder sb = new StringBuilder();

        sb.append("codeCompte: ");
        sb.append(this.getCodeCompte());

        sb.append(", dateCreation: ");
        sb.append(this.getDateCreation());

        sb.append(", sold: ");
        sb.append(this.getSolde());

        //sb.append(", client: ");
        //sb.append(this.getClient());

        return sb.toString();

    }

}
