package com.epf.rentmanager.servlet.client;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/users/edit")
public class ClientModifServlet extends HttpServlet {
    @Autowired
    ClientService clientService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        try {
            Client client = clientService.findById(id);
            System.out.print(client);
            request.setAttribute("last_name",client.getName());
             request.setAttribute("first_name",client.getPrenom());
             request.setAttribute("email",client.getEmail());
             request.setAttribute("birth",client.getNaissance());
        }catch (ServiceException e) {
            throw new ServletException(e);
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        String name =  request.getParameter("last_name");
        String prenom =  request.getParameter("first_name");
        String email =  request.getParameter("email");
        LocalDate birth = LocalDate.parse(request.getParameter("birth"));
        /*
        try {
            //clientService.create(new Client(1, name,prenom,email,birth));
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        */

        response.sendRedirect("/rentmanager/users");
    }
}
