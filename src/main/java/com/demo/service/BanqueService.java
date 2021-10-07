package com.demo.service;

import com.demo.dao.ClientRepository;
import com.demo.dao.CompteRepository;
import com.demo.dao.OperationRepository;
import com.demo.entities.*;
import com.demo.service.exception.InsufficientBalance;
import com.demo.service.exception.MissingAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class BanqueService implements IBanqueService {

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Compte consulter(String codeCompte) {

        try{

            Optional<Compte> compteOptional = compteRepository.findById(codeCompte);    // Better use findBy! Can avoid to throw an exception when the proxy is used on a non existing element!
            Compte result = compteOptional.get();

            return result;

        }
        catch (Exception e){
            throw new MissingAccountException("Compte introuvable");   // Pour ne pas surveiller l'exception.
        }

    }

    @Override
    public void verser(String codeCompte, Double montant) {

        Compte compte = this.consulter(codeCompte);
        Versement versement = new Versement(new Date(), montant, compte);

        operationRepository.save(versement);
        compte.setSolde(compte.getSolde() + montant);
        compteRepository.save(compte);

    }

    @Override
    public void retirer(String codeCompte, Double montant) {

        Compte compte = this.consulter(codeCompte);
        if(!this.peutRetirer(compte, montant)){
            throw new InsufficientBalance("Solde insuffisant");
        }

        Retrait retrait = new Retrait(new Date(), montant, compte);
        operationRepository.save(retrait);
        compte.setSolde(compte.getSolde() - montant);
        compteRepository.save(compte);

    }

    /**
     * Checks if an account can be punctured
     * @param compte
     * @param montant
     * @return boolean
     */
    private boolean peutRetirer(Compte compte, Double montant){

        if(compte instanceof CompteCourant){

            Double facilitesCaisse = 0.0;
            facilitesCaisse = ((CompteCourant) compte).getDecouvert();

            if(compte.getSolde() + facilitesCaisse < montant){  // Le montant à retirer est superieur a la capacite reelle du compte
                return false;
            }

        }

        return true;

    }

    @Override
    public void virement(String codeCompte1, String codeCompte2, Double montant) {

        this.retirer(codeCompte1, montant);
        this.verser(codeCompte2, montant);

    }

    /**
     * Retrive operations by pagination (Page)
     * @param codeCompte
     * @param page
     * @param size
     * @return Page<Operation>
     */
    @Override
    public Page<Operation> operations(String codeCompte, int page, int size) {
        //new PageRequest(page, size)
        return operationRepository.operations(codeCompte, PageRequest.of(page, size));

    }

    /**
     * Retrive operations by pagination (Page)
     * @param page
     * @param size
     * @return Page<Operation>
     */
    @Override
    public Page<Operation> operations(int page, int size) {

        return operationRepository.operations(PageRequest.of(page, size));

    }

    @Override
    public Page<Client> clients(int page, int size){

        return clientRepository.clients(PageRequest.of(page, size));

    }

}
