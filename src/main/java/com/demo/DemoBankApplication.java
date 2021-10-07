package com.demo;

import com.demo.dao.ClientRepository;
import com.demo.dao.CompteRepository;
import com.demo.dao.OperationRepository;
import com.demo.entities.*;
import com.demo.service.IBanqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Project core done following the next videos: https://www.youtube.com/watch?v=u2z-oTMAVKY (49min)        https://www.youtube.com/watch?v=C6X-8ewOx8k
 * Run the application.
 * Serve as a mini bootstrap to initiate some datas to test on and check.
 * CommandLineRunner implements the run method which is being run after the main. Is a better thing to so than using the SpringApplication to add the datas.
 */
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})   // To exclude the initial login page. To replace afterward by SecurityConfig 'security.httpBasic().disable()'. Check https://stackoverflow.com/questions/23636368/how-to-disable-spring-security-login-screen
public class DemoBankApplication implements CommandLineRunner {

    @Autowired  // Injection automatique
    private ClientRepository clientRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private IBanqueService banqueService; // Pour tester

    public static void main(String[] args) {

        //ApplicationContext ctx = SpringApplication.run(DemoBankApplication.class, args); // Premiere methode, faire ctx.getBean(LaClasseRepository) et faire des saves dessus.
        SpringApplication.run(DemoBankApplication.class, args);

    }

    /**
     * Method is run after the main.
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        try {

            // Uncomment when in create mode
            /*Collection<Client> clients = createClients();
            Collection<Compte> comptes = createComptes(clients);
            Collection<Operation> operations = createOperations(comptes);

            testerServices();*/

        }
        catch(Exception e){

            System.out.println("EXCEPTION DETECTED");
            System.out.println("------------------");

            throw e;

        }

    }

    private Collection<Client> createClients(){

        //ClientRepository clientRepository = ctx.getBean(ClientRepository.class);
        List<Client> clients = new ArrayList<Client>();
        clients.add(clientRepository.save(new Client("Gerard", "Demaindieux", "gerard@gmail.com")));
        clients.add(clientRepository.save(new Client("Nicolas", "Sarkozu", "nicolas@gmail.com")));

        return clients;

    }

    private Collection<Compte> createComptes(Collection<Client> clients){

        Date now = new Date();
        Object[] arrayClients = clients.toArray();

        List<Compte> comptes = new ArrayList<Compte>();
        comptes.add(compteRepository.save(new CompteEpargne("0001", now, 1000.0, (Client) arrayClients[0], 2.0)));
        comptes.add(compteRepository.save(new CompteEpargne("0002", now, 2000.0, (Client) arrayClients[1], 3.0)));

        comptes.add(compteRepository.save(new CompteCourant("0003", now, 1000.0, (Client) arrayClients[0], 500.0)));

        return comptes;

    }

    private Collection<Operation> createOperations(Collection<Compte> comptes){

        Date now = new Date();
        Object[] arrayComptes = comptes.toArray();

        List<Operation> operations = new ArrayList<Operation>();
        operations.add(operationRepository.save(new Versement(now, 200.0, (Compte) arrayComptes[0])));
        operations.add(operationRepository.save(new Versement(now, 500.0, (Compte) arrayComptes[0])));
        operations.add(operationRepository.save(new Retrait(now, 100.0, (Compte) arrayComptes[0])));
        // Compte 0001 a 600

        operations.add(operationRepository.save(new Versement(now, 1000.0, (Compte) arrayComptes[1])));
        operations.add(operationRepository.save(new Versement(now, 150.0, (Compte) arrayComptes[1])));
        operations.add(operationRepository.save(new Retrait(now, 200.0, (Compte) arrayComptes[1])));
        operations.add(operationRepository.save(new Retrait(now, 150.0, (Compte) arrayComptes[1])));
        // Compte 0002 a 800

        return operations;

    }

    private void testerServices(){

        try {

            Compte compte0001 = banqueService.consulter("0001");
            Compte compte0002 = banqueService.consulter("0002");

            /*System.out.println("ACCOUNTS");
            System.out.println("--------");

            System.out.println(compte0001.toString());
            System.out.println(compte0002.toString());*/

            banqueService.verser("0001", 100.0);

            /*System.out.println("AFTER VERSEMENT 0001 (100.0)");
            System.out.println("----------------------------");

            System.out.println(compte0001.toString());
            System.out.println(compte0002.toString());*/

            banqueService.retirer("0002", 200.0);

            /*System.out.println("AFTER VERSEMENT 0002 (200)");
            System.out.println("--------------------------");

            System.out.println(compte0001.toString());
            System.out.println(compte0002.toString());*/

            banqueService.virement("0001", "0002", 200.0);

            /*System.out.println("AFTER VIREMENT 0001 -> 0002 (200)");
            System.out.println("---------------------------------");

            System.out.println(compte0001.toString());
            System.out.println(compte0002.toString());*/

        }
        catch(Exception e){

            System.out.println("Exception occured");
            System.out.println(e);

        }

    }

}
