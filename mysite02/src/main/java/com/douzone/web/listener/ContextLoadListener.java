package com.douzone.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ContextLoadListener implements ServletContextListener {	
   
	public void contextDestroyed(ServletContextEvent servletContextEvent)  { 
		ServletContext context = servletContextEvent.getServletContext(); // context는 전역
		String contextConfigLocation = context.getInitParameter("contextConfigLocation");
		
		System.out.println("Application Starts..." + contextConfigLocation);
    }

    public void contextInitialized(ServletContextEvent arg0)  { 

    }
	
}
