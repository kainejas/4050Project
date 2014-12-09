package edu.uga.dawgtrades.boundary;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.Item;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FindItems extends HttpServlet{
    private static final long serialVersionUID = 1L;

    static  String            templateDir = "WEB-INF/templates";
    static  String            resultTemplateName = "FindAllClubs-Result.ftl";

    private Configuration     cfg;

    public void init(){
        cfg = new Configuration();
        cfg.setServletContextForTemplateLoading( getServletContext(), "WEB-INF/templates" );
    }

    public void doGet( HttpServletRequest  req, HttpServletResponse res )throws ServletException, IOException{
        Template resultTemplate = null;
        BufferedWriter toClient = null;
        ObjectModel objectModel = null;
        Logic logic = null;
        String category_name = null;
        List<Item> foundItems = null;
        List<List<Object>> items = null;
        List<Object> item = null;
        Item i = null;
        HttpSession httpSession;
        Session session;
        String ssid;

        try {
            resultTemplate = cfg.getTemplate( resultTemplateName );
        } 
        catch( IOException e ) {
            throw new ServletException( 
                    "Can't load template in: " + templateDir + ": " + e.toString());
        }
        
        toClient = new BufferedWriter(new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() ));

        res.setContentType("text/html; charset=" + resultTemplate.getEncoding());
        
        httpSession = req.getSession();
        if( httpSession == null ) {       // assume not logged in!
            DawgTradesError.error( cfg, toClient, "Session expired or illegal; please log in" );
            return;
        }
        
        ssid = (String) httpSession.getAttribute( "ssid" );
        if( ssid == null ) {       // not logged in!
            DawgTradesError.error( cfg, toClient, "Session expired or illegal; please log in" );
            return;
        }

        session = SessionManager.getSessionById( ssid );
        objectModel = session.getObjectModel();
        if( objectModel == null ) {
            DawgTradesError.error( cfg, toClient, "Session expired or illegal; please log in" );
            return; 
        }
        
        logic = new LogicImpl( objectModel );

        category_name = req.getParameter("category_name");
        if(category_name == null){
        	DawgTradesError.error(cfg, toClient, "Unspecified category name");
        	return;
        }
        
        try{
        	foundItems = logic.findItems(category_name);
        }
        catch(Exception e){
        	DawgTradesError.error(cfg, toClient, e);
        	return;
        }
        
        Map<String,Object> root = new HashMap<String,Object>();
        
        root.put("category_name", category_name);
        items = new LinkedList<List<Object>>();
        root.put("items", items);
        
        for(int j = 0; j < foundItems.size(); j++){
        	i = (Item) foundItems.get(j);
        	item = new LinkedList<Object>();
        	item.add(i.getId());
			item.add(i.getName());
			item.add(i.getDescription());
			item.add(i.getOwnerId());
			item.add(i.getCategoryId());
			items.add(item);
        }
        
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

