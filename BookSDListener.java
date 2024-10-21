package bookSearchDisplay;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class BookSDListener implements ServletContextListener{
    
    private Connection conn;
    
    @Override
    public void contextInitialized(ServletContextEvent ev){
        ServletContext ctxt=ev.getServletContext();
        String connurl=ctxt.getInitParameter("connurl");
        String dbusername=ctxt.getInitParameter("dbusername");
        String dbpwd=ctxt.getInitParameter("dbpwd");

        try{
            conn=DriverManager.getConnection(connurl, dbusername,dbpwd);
            System.out.println("\nConnected successfully to the DB. Hurray! in listener\n");
            ctxt.setAttribute("dbconn",conn);
        }catch(Exception ex){
            System.out.println("\nSome problem in listener: "+ex+"\n");
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent ev)
    {
        try{
            if(conn!=null){
                conn.close();
            }
        }catch(Exception ex){
            System.out.println("Exception in closing:"+ex);
        }
    }
}
