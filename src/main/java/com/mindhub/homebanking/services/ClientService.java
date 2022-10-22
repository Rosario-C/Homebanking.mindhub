package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {
    //pirmero hacemos un metodo para obtener una lista de clientes dto
    public List<ClientDTO> getClientsDTO();
    public Client getClientCurrent(Authentication authentication);
    ClientDTO getClientDTO(Long id);
    void saveClient(Client client); //lo hacemos vacio para que nada mas guarde los clientes, recordando que aca definimos lo metodos para después darles una utilidad en implments
    Client getClientByEmail(String email);
}