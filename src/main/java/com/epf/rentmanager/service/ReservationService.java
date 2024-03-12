package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public long create(Reservation reservation) throws ServiceException {

        try{
            long  id=reservationDao.create(reservation);
            reservation.setId(id);
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteById(long id) throws ServiceException{
        try{
            boolean done =reservationDao.delete(id);
            if(!done){
                throw new ServiceException("Le reservation n°" +id + " n'a pas été supprimé dans la base de donnée");
            }
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la suppression du reservation");
        }
    }
    public List<Reservation> findAll() throws ServiceException {
        try {
            List<Reservation> lreservation = reservationDao.findAll();
            return lreservation;
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la récupération de la liste des reservation");
        }
    }
    public int countReservation() throws ServiceException{
        try {
            return reservationDao.countReservationDao();
        }catch (DaoException e) {
            throw new ServiceException("Erreur lors du contage du nombre de reservation");
        }
    }





}
