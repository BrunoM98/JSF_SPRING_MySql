package bm.fit_zone.Service;

import bm.fit_zone.model.Client;

import java.util.List;

public interface IClientService {

    public List<Client> clientList();

    public Client serchClient(Integer id);

    public void saveClient(Client client);

    public void deleteClient(Client client);

}
