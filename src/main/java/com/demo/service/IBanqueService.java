package com.demo.service;

import com.demo.entities.Client;
import com.demo.entities.Compte;
import com.demo.entities.Operation;
import org.springframework.data.domain.Page;

public interface IBanqueService {

    public Compte consulter(String codeCompte);
    public void verser(String codeCompte, Double montant);
    public void retirer(String codeCompte, Double montant);
    public void virement(String codeCompte1, String codeCompte2, Double montant);
    public Page<Operation> operations(String codeCompte, int page, int size);   // Page = permet de recuperer , size = taille de la page
    public Page<Operation> operations(int page, int size);
    public Page<Client> clients(int page, int size);

}
