package bm.fit_zone.controller;

import bm.fit_zone.Service.IClientService;
import bm.fit_zone.model.Client;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
@Data
@ViewScoped
public class IndexController {

    @Autowired
    IClientService clientService;
    private List<Client> clients;
    private Client selectedClient;
    // VITACORA
    private static final Logger logger =
            LoggerFactory.getLogger(IndexController.class);

    @PostConstruct
    public void init() {
        chargueData();
    }

    public void chargueData() {
//        llamada a la lista de clientes mediante logger
        this.clients = this.clientService.clientList();
        this.clients.forEach(client -> logger.info(client.toString()));
    }

    public void chargueClient() {
//        se crea un objeto para que la funcion de clientservice utilize y llene el mismo
        this.selectedClient = new Client();
    }

    public void saveClient() {
//        vemos la informacion que se guarda en la base de datos
        logger.info("Client to Save: " + this.selectedClient);
//        revisamos si el cliente existo por su id en el caso e que no exista lo guarda en la base de datos
        if (this.selectedClient.getIdclient() == null) {
            this.clientService.saveClient(selectedClient);
//            recuperamos los datos de la memoria
            this.clients.add(this.selectedClient);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Save a Client in Data Base"));
        }
//        Ocultamos la ventana modal
        PrimeFaces.current().executeScript("PF('windowModalClient').hide()");
//        actualizamos la ventana usando ajax
        PrimeFaces.current().ajax().update("form-clients:massage",
                "form-clients:clients-table");
//        Restarteamos selectedClient
        this.selectedClient = null;

    }
}

