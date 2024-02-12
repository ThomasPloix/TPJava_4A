package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Reservation;

public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;
	
	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}
	
	
	public long create(Reservation client) throws ServiceException {
		// TODO: créer un client
		return 0;
	}

	public Reservation findById(long id) throws ServiceException {
		// TODO: récupérer un client par son id
		return new Reservation();
	}

	public List<Reservation> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		return new ArrayList<Reservation>();
	}
	
}
