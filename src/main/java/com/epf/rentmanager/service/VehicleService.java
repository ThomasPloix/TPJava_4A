package com.epf.rentmanager.service;

import java.util.List;


import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;

public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}
	
	
	public long Create(Vehicle vehicle) throws ServiceException {
		if(vehicle.getSeats()<=1 || vehicle.getConstructeur().isEmpty()){
			throw new ServiceException("Erreur lors de la création du vehicule; nombre de place <1");
		}
		try{
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
    }

	public void deleteById(long id) throws ServiceException{
		try{
			boolean done =vehicleDao.delete(id);
			if(!done){
			throw new ServiceException("Le vehicule n°" +id + " n'a pas été supprimé dans la base de donnée");
			}
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la suppression du vehicule");
		}
    }

	public Vehicle findById(long id) throws ServiceException {
		try{
			Vehicle vehicle = vehicleDao.findById(id);
			if(vehicle!=null){
				return vehicle;
			}
			throw new ServiceException("Le vehicule n°" +id + " n'a pas été retrouvé dans la base de donnée");
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération du vehicule");
		}
    }

	public List<Vehicle> findAll() throws ServiceException {
		try {
			List<Vehicle> lvehicule = vehicleDao.findAll();
			return lvehicule;
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération de la liste des vehicules");
		}
    }
	public int countVehicule() throws ServiceException{
		try {
            return vehicleDao.countVehiculeDao();
		}catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération de la liste des vehicules");
		}
	}
	
}
