 package bookSearchDisplay;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchBook extends HttpServlet {
    
//    private static Connection conn;
    
//    @Override
//    public void init(){
//        try{
//            conn = DriverManager.getConnection("jdbc:oracle:thin:@//DESKTOP-533TJJV:1521/xe", "user2", "abcd");
//            ServletContext ctxt = super.getServletContext();
//            ctxt.setAttribute("dbconn", conn);
//        }catch(SQLException sq){
//            System.out.println("Exception occured: " + sq);
//        }
//    }
/*
     try{
            conn=DriverManager.getConnection(connurl, dbusername,dbpwd);
            System.out.println("\nConnected successfully to the DB\n");
            ctxt.setAttribute("dbconn",conn);
        }catch(Exception ex){
            System.out.println("\nSome problem in listener: "+ex+"\n");
        }
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<title>Servlet SearchBook</title>");            
            pw.println("</head>");
            pw.println("<body>");
            String subject = request.getParameter("subject");
            RequestDispatcher rd = null;
            if(subject.isEmpty()){
                pw.println("<span style = 'color:crimson'>Please input subject</span>");
                rd = request.getRequestDispatcher("index.html");
                rd.include(request, response);
            }else{
            ServletContext ctxt = super.getServletContext();
            try{
                Connection conn = (Connection)ctxt.getAttribute("dbconn");
                PreparedStatement ps = conn.prepareStatement("select * from booksrecord where subject = ?");
                ps.setString(1, subject);
                ResultSet rs = ps.executeQuery();
                ArrayList<String> booklist = new ArrayList<>();
                ArrayList<Double> bookprice = new ArrayList<>();
                while(rs.next()){
                    booklist.add(rs.getString(1));
                    bookprice.add(rs.getDouble(3));
                }
                request.setAttribute("names",booklist);
                request.setAttribute("prices",bookprice);
                rd = request.getRequestDispatcher("DisplayBook");
                rd.forward(request, response);
            }catch(SQLException sq){
                System.out.println("Exception: " + sq.getMessage());
            }catch(NullPointerException sq){
                System.out.println("Exception: " + sq);
            }
           }
            pw.println("</body>");
            pw.println("</html>");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
