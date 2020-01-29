package com.github.jwong12.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import com.github.jwong12.exceptions.ApplicationException;

import com.github.jwong12.services.EmployeeServices;

/**
 * Servlet implementation class ServiceServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LOG4J_CONFIG_FILENAME = "log4j2.xml";
	private static Logger LOG = LogManager.getLogger(MainServlet.class);
	private EmployeeServices employeeServices;
	boolean firstLogin;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    		configureLogging();
        	try {
        		employeeServices = new EmployeeServices();
        		
			} catch (ApplicationException e) {
				LOG.error(e.getMessage());
			}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		firstLogin = true;
		String addDescription = null;
		String findDescription = null;
		String remDescription = null;
		String employeeIdAdd = request.getParameter( "employeeIdAdd" );
		String employeeIdFind = request.getParameter( "employeeIdFind" );
		String employeeIdRem = request.getParameter( "employeeIdRem" );
		
		if( employeeIdAdd != null && employeeIdAdd.trim().length() > 0 ) {
			String firstName = request.getParameter( "firstName" ).trim();
			String lastName = request.getParameter( "lastName" ).trim();
			String date = request.getParameter( "date" );
			
			try {
				employeeServices.addEmployee(employeeIdAdd.trim(), firstName, lastName, date);
				firstLogin = false;
				
			} catch (DateTimeParseException e) {
				LOG.error(e.getMessage());
				response.setStatus(903);
				
			} catch (SQLException e) {
				LOG.error(e.getMessage());
				if(e.getSQLState().equals("23000")) {
					response.setStatus(502);
				} 
				
			} catch (ApplicationException e) {
				LOG.error(e.getMessage());
				response.setStatus(901);
			}
		}
		
		if( employeeIdFind != null && employeeIdFind.trim().length() > 0 ) {
			try {
				String employeeFound = employeeServices.findEmployees(employeeIdFind.trim());
				
				if(employeeFound != null) {
					response.setStatus(0);
					request.setAttribute("employeeFound", employeeFound);
					
				} else {
					response.setStatus(801);
				}
				
			} catch (SQLException | ApplicationException e) {
				LOG.error(e.getMessage());
				response.setStatus(801);
			}
		}
		
		if( employeeIdRem != null && employeeIdRem.trim().length() > 0 ) {
			try {
				employeeServices.removeEmployee(employeeIdRem.trim());
				response.setStatus(001);

			} catch (SQLException | ApplicationException e) {
				LOG.error(e.getMessage());
				response.setStatus(902);
			}
		}
		
		if(response.getStatus() == 200 && firstLogin == false) {
			addDescription = "Result Code: " + response.getStatus() + " Description: Success.";
		
		} else if(response.getStatus() == 502) {
			addDescription = "Result Code: " + response.getStatus() + " Description: ID already exists for another employee.";
			
		} else if(response.getStatus() == 901) {
			addDescription = "Result Code: " + response.getStatus() + " Description: Invalid employee data!";
			
		} else if(response.getStatus() == 903) {
			addDescription = "Result Code: " + response.getStatus() + " Description: Invalid date!";
			
		} else if(response.getStatus() == 0) {
			findDescription = "Result Code: 000 Description: Success.";
			
		} else if(response.getStatus() == 801) {
			findDescription = "Result Code: " + response.getStatus() + " Description: No match found.";
			
		} else if(response.getStatus() == 1) {
			remDescription = "Result Code: 001 Description: Deleted Successfully.";
			
		} else if(response.getStatus() == 902) { 
			remDescription = "Result Code: " + response.getStatus() + " Description: Delete Unsuccessful.";
	
		} 
		
		try {
			request.setAttribute("employeeList", employeeServices.getEmployeeList());
		} catch (SQLException | ApplicationException e) {
			LOG.error(e.getMessage());
		}
		
		request.setAttribute("findDescription", findDescription);
		request.setAttribute("addDescription", addDescription);
		request.setAttribute("remDescription", remDescription);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void configureLogging() {
		ConfigurationSource source;
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = null;
		
		try {
			inputStream = classLoader.getResourceAsStream(LOG4J_CONFIG_FILENAME);
			source = new ConfigurationSource(inputStream);
			Configurator.initialize(null, source);
        	LOG.info("Logging installed succesfully!");

		} catch (IOException e) {
			System.out.println(String.format("Can't find the log4j logging configuration file %s.", LOG4J_CONFIG_FILENAME));
		}
	}
}
