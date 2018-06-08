package youtube.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import youtube.dao.ConnectionManager;

public class InitListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent arg0)  { 
      ConnectionManager.close();
    }

    public void contextInitialized(ServletContextEvent arg0)  { 
        System.out.println("Inicijalizacija");
        
        ConnectionManager.open();
        
        System.out.println("Završeno");
    }
	
}
