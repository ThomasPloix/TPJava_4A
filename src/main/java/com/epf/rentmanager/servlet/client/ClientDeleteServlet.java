package com.epf.rentmanager.servlet.client;

import com.epf.rentmanager.service.ClientService;
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

@WebServlet("/users/delete")
public class ClientDeleteServlet extends HttpServlet {
    @Autowired
    ClientService clientService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id=request.getParameter("id");
        try {
            clientService.delete(Long.parseLong(id));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("/rentmanager/users");
    }
}
