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
import edu.uga.dawgtrades.logic.Logic;
import edu.uga.dawgtrades.logic.impl.LogicImpl;
import edu.uga.dawgtrades.model.*;
import edu.uga.dawgtrades.persist.*;
import edu.uga.dawgtrades.persist.impl.*;
import edu.uga.dawgtrades.model.impl.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.sql.Connection;


public class Unregister extends HttpServlet{
	private static final long serialVersionUID = 1L;
	static String templateDir = "WEB-INF/templates";
	static String resultTemplateName = "Goodbye.ftl";

	private Configuration cfg;

	public void init(){
		cfg = new Configuration();
		cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/templates");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        
        Template       resultTemplate = null;
        HttpSession    httpSession = null;
        BufferedWriter toClient = null;
        String         username = null;
        String         ssid = null;
        Session        session = null;
        ObjectModel     objectModel = null;
        Logic logic = null;
        // Load templates from the WEB-INF/templates directory of the Web app.
        //
        try {
            resultTemplate = cfg.getTemplate( resultTemplateName );
        }
        catch (IOException e) {
            throw new ServletException( "ShowMainWindow.doPost: Can't load template in: " + templateDir + ": " + e.toString());
        }
        
        
        // Prepare the HTTP response:
        // - Use the charset of template for the output
        // - Use text/html MIME-type
        //
        toClient = new BufferedWriter( new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() ) );
        res.setContentType("text/html; charset=" + resultTemplate.getEncoding());
        
        httpSession = req.getSession();
        if( httpSession == null ) {       // not logged in!
            DawgTradesError.error( cfg, toClient, "Session expired or illegal; please log in" );
            return;
        }
        
        ssid = (String) httpSession.getAttribute( "ssid" );
        if( ssid == null ) {       // assume not logged in!
            DawgTradesError.error( cfg, toClient, "Session expired or illegal; please log in" );
            return;
        }
        
        session = SessionManager.getSessionById( ssid );
        objectModel = session.getObjectModel();
        if( objectModel == null ){
            DawgTradesError.error( cfg, toClient, "Session expired or illegal; please log in" );
            return;
        }
        
        RegisteredUser person = session.getUser();
        if( person == null ) {
            DawgTradesError.error( cfg, toClient, "Session expired or illegal; please log in" );
            return;
        }
        
        

		logic = new LogicImpl(objectModel);
        try {
            SessionManager.logout(session);
        logic.unregister(person.getName());
        }
        catch(Exception e) {
            DawgTradesError.error(cfg, toClient, "Error deleting user: "  + person.getName() + "\t\n" + e.getMessage());
            return;
        }
        

		Map<String, Object> root = new HashMap<String, Object>();

        try{
			resultTemplate.process(root, toClient);
			toClient.flush();
		}
		catch(TemplateException e){
			throw new ServletException("Error while processing FreeMarker template", e);
		}

		toClient.close();
	}
}
