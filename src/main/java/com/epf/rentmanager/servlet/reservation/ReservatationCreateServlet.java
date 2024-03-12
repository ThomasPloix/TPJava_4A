package com.epf.rentmanager.servlet.reservation;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/rents/create")
public class ReservatationCreateServlet extends HttpServlet {
    @Autowired
    ReservationService reservationService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Client> lclient =clientService.findAll();
            List<Vehicle> lvehicule = vehicleService.findAll();
            request.setAttribute("clientlist", lclient);
            request.setAttribute("carlist",lvehicule);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }


        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        long clientId = Long.parseLong( request.getParameter("client"));
        long carId =  Long.parseLong( request.getParameter("car"));
        LocalDate begin = LocalDate.parse(request.getParameter("begin"));
        System.out.println(begin);
        LocalDate  end = LocalDate.parse(request.getParameter("end"));

        try {
             reservationService.create(new Reservation((long) 1,clientId,carId,begin,end));
        } catch (ServiceException e) {
            throw new RuntimeException("Erreur lors de la création de la reservation");
        }
        response.sendRedirect("/rentmanager/rents");
    }

}
