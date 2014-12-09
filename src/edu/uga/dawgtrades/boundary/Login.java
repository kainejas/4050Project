package edu.uga.dawgtrades.boundary;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Login 
    extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    static  String  templateDir = "WEB-INF/templates";
    static  String  resultTemplateName = "MainWindow.ftl";

    private Configuration  cfg; 

    public void init() 
    {
        // Prepare the FreeMarker configuration;
        // - Load templates from the WEB-INF/templates directory of the Web app.
        //
        cfg = new Configuration();
        cfg.setServletContextForTemplateLoading(
                getServletContext(), 
                "WEB-INF/templates"
                );
    }
    
    public void doGet( HttpServletRequest req, HttpServletResponse res )
    throws ServletException, IOException
    {
        HttpSession    httpSession = null;

        httpSession = req.getSession();
        if( httpSession == null || (String) httpSession.getAttribute( "ssid" ) == null ) {
            // not logged in!
            res.sendRedirect("http://uml.cs.uga.edu:8080/team3/login.html");
        }
        else {
            res.sendRedirect("http://uml.cs.uga.edu:8080/team3/ShowMainWindow");
        }
        
        
    }
    
    

    public void doPost( HttpServletRequest req, HttpServletResponse res )
            throws ServletException, IOException
    {
        Template       resultTemplate = null;
        HttpSession    httpSession = null;
        BufferedWriter toClient = null;
        String         username = null;
        String         password = null;
        String         ssid = null;
        Session        session = null;

        // Load templates from the WEB-INF/templates directory of the Web app.
        //
        try {
            resultTemplate = cfg.getTemplate( resultTemplateName );
        } 
        catch (IOException e) {
            throw new ServletException( "Login.doPost: Can't load template in: " + templateDir + ": " + e.toString());
        }

        httpSession = req.getSession();
        ssid = (String) httpSession.getAttribute( "ssid" );
        if( ssid != null ) {
            System.out.println( "Already have ssid: " + ssid );
            session = SessionManager.getSessionById( ssid );
            System.out.println( "Connection: " + session.getConnection() );
        }
        else
            System.out.println( "ssid is null" );

        // Prepare the HTTP response:
        // - Use the charset of template for the output
        // - Use text/html MIME-type
        //
        toClient = new BufferedWriter( new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() ) );

        res.setContentType("text/html; charset=" + resultTemplate.getEncoding());

        // Get the parameters
        //
        username = req.getParameter( "user_name" );
        password = req.getParameter( "password" );

        if( username == null || password == null ) {
            DawgTradesError.error( cfg, toClient, "Unknown user name or password" );
            return;
        }

        try {
            ssid = SessionManager.login( username, password );
            System.out.println( "Obtained ssid: " + ssid );
            try{
            httpSession.setAttribute( "ssid", ssid );
            }
            catch(Exception e) {
                DawgTradesError.error(cfg, toClient, e);
            }
            session = SessionManager.getSessionById( ssid );
            System.out.println( "Connection: " + session.getConnection() );
        } 
        catch ( Exception e ) {
            DawgTradesError.error( cfg, toClient, e );
            return;
        }

        // Setup the data-model
        //
        Map<String, String> root = new HashMap<String, String>();

        // Build the data-model
        //
        root.put( "user_name", username );

        // Merge the data-model and the template
        //
        try {
            resultTemplate.process( root, toClient );
            toClient.flush();
        } 
        catch (TemplateException e) {
            throw new ServletException( "Error while processing FreeMarker template", e);
        }

        toClient.close();
    }
}
