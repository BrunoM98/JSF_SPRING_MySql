package bm.fit_zone.Service;

import bm.fit_zone.model.Client;
import bm.fit_zone.repository.IClietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class ClientService implements IClientService {

    Scanner read = new Scanner(System.in);

// Inject the dependencies from the repository
    @Autowired
    private IClietRepository iClietRepository;


    @Override
    public List<Client> clientList() {
        List<Client> client = iClietRepository.findAll();

        return client;
    }

    @Override
    public Client serchClient(Integer id) {
        // orElse is used to know that if we don't have that ID in the database, it returns null.
        Client client = iClietRepository.findById(id).orElse(null);

        return client;
    }

    @Override
    public void saveClient(Client client) {
        // If the client is null, an insert is performed; otherwise, an update is executed.
        iClietRepository.save(client);
    }

    @Override
    public void deleteClient(Client client) {
        iClietRepository.delete(client);
    }
}
