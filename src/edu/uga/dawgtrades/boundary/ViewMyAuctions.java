package edu.uga.dawgtrades.boundary;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.logic.*;
import edu.uga.dawgtrades.logic.impl.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ViewMyAuctions
    extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    static  String  templateDir = "WEB-INF/templates";
    static  String  resultTemplateName = "ViewMyAuctions.ftl";

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
        Template       resultTemplate = null;
        HttpSession    httpSession = null;
        BufferedWriter toClient = null;
        String         username = null;
        String         ssid = null;
        Session        session = null;
        ObjectModel     objectModel = null;
        Logic           logic = null;

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
        // Setup the data-model
        
        try {
            auctionList = logic.viewMyAuctions(person);
        }
        catch(Exception e) {
             DawgTradesError.error( cfg, toClient, "Error logic.viewMyAuctions()    " + e.getMessage() );
        }
        Map<String, Object> root = new HashMap<String, Object>();
        List<Map<String,String>> tempList = new ArrayList<Map<String, String>>();
        HashMap<String, String> auctionMap = new HashMap<String, String>();
        root.put("user_name", person.getName());
            
     
        try {
          auctionList = logic.viewMyAuctions(person);
            Auction auc = null;
            int count = 0;
            while(auctionList.hasNext()) {
                auc = auctionList.next();
                auctionMap = new HashMap<String, String>();
                auctionMap.put("id", auc.getId());
                auctionMap.put("min_price", auc.getMinPrice());
                auctionMap.put("item_id", auc.getItemId());
                auctionMap.put("expiration", auc.getExpiration().toString());
                tempList.add(auctionMap);

            } catch(Exception e) {
                awgTradesError.error( cfg, toClient, "Error making tempList for Page   " + e.getMessage() );
            }
            
            root.put("auctions", tempList);
        }
      
            
    
        // Build the data-model
        //
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
