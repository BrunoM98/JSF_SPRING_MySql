package bm.fit_zone;

import bm.fit_zone.Service.ClientService;
import bm.fit_zone.menu.Menu;
import bm.fit_zone.model.Client;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import bm.fit_zone.Service.IClientService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

//@SpringBootApplication
public class FitZoneApplication implements CommandLineRunner {


    @Autowired
    private IClientService clientService;

    private static final Logger logger = LoggerFactory.getLogger(FitZoneApplication.class);

    String nl = System.lineSeparator();

    public static void main(String[] args) {
        Menu menu = new Menu();

        logger.info("Initializing project");
        SpringApplication.run(FitZoneApplication.class, args);
        logger.info("Finish Application");

    }
    @Override
    public void run(String... args) throws Exception {
        zonafitAPP();
    }
    private void zonafitAPP(){
        boolean out = false;
        Scanner read = new Scanner(System.in);
        while (!out){
            int option = showMenu(read);
            out = executeOption(read, option);
            logger.info(nl);

        }
    }
    private int showMenu(Scanner read){
        logger.info(nl + """
            Fit Zone Application >D
            1: Client List
            2: Search Client
            3: Save Client
            4: Modify Client
            5: Delete Client;
            6: Exit
            Choose One Option:\s""");

        return Integer.parseInt(read.nextLine());
    }

    private boolean executeOption(Scanner read, int option){
        boolean out = false;
        switch (option){
            case 1 -> {
                logger.info(nl + "Client List " + nl);

                List<Client> clients = clientService.clientList();

                clients.forEach(client -> logger.info("{}{}", client.toString(), nl));
            }
            case 2 -> {
                logger.info(nl + " Search Client for ID " + nl);

                int idClient = Integer.parseInt(read.nextLine());

                Client client = clientService.serchClient(idClient);

                if (client != null)
                    logger.info(" Client Searching Successful " + client +nl);
                else
                    logger.info("Client not Exist");
            }
            case 3 -> {
                logger.info(" Save Client" + nl);
                logger.info("Name");
                String name = read.nextLine();
                logger.info("SurName");
                String surName = read.nextLine();
                logger.info("Memer number");
                int member = Integer.parseInt(read.nextLine());
                Client client = new Client();
                client.setName(name);
                client.setSurname(surName);
                client.setMember(member);
                clientService.saveClient(client);
                logger.info(" Save a Client " + client + nl);
            }
            case 4 -> {
                logger.info("Modify Client" + nl);
                logger.info("Set ID");
                int idClient = Integer.parseInt(read.nextLine());
                Client client = clientService.serchClient(idClient);

                if(client != null) {
                    logger.info("Name");
                    String name = read.nextLine();
                    logger.info("Surname");
                    String surName = read.nextLine();
                    logger.info("Member Number");
                    int member = Integer.parseInt(read.nextLine());

                    client.setName(name);
                    client.setSurname(surName);
                    client.setMember(member);
                    clientService.saveClient(client);
                    logger.info("Modify Client " + client + nl);
                }else{
                    logger.info(" Client not Exist " + nl);
                }
            }
            case 5 -> {
                logger.info(nl + " Delete Client " + nl);
                logger.info("ID Client");
                int idClient = Integer.parseInt(read.nextLine());
                Client client = clientService.serchClient(idClient);
                if(client != null){
                    clientService.deleteClient(client);
                    logger.info("Client Delete" + nl);
                } else {
                    logger.info("Client Not Exist");
                }

            }
            case 6 -> {
                logger.info("Exit");
                out = true;
            }
            default -> logger.info("Choose a Correct Option" + option + nl);

        }
        return out;
    }

    }

