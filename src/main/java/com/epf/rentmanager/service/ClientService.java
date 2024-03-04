package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;
	
	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	
	public long create(Client client) throws ServiceException {
		if(client.getName().isEmpty() || client.getPrenom().isEmpty()){
			throw new ServiceException("Erreur lors de la création Client Name or Surname null");
		}
		try{
			client.setName(client.getName().toUpperCase());
			return clientDao.create(client);
		} catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

	public Client findById(long id) throws ServiceException {
		try{
			Client client = clientDao.findById(id);
			if(client!=null){
				return client;
			}
			throw new ServiceException("Le client n°" +id + " n'a pas été retrouvé dans la base de donnée");
		} catch (DaoException e) {
            throw new ServiceException("Erreur lors de la récupération du client");
        }
	}

	public List<Client> findAll() throws ServiceException {
		try {
			List<Client> lClients = clientDao.findAll();
			return lClients;
		} catch (DaoException e) {
            throw new ServiceException("Erreur lors de la récupération de la liste des clients");
        }
	}
	
}
