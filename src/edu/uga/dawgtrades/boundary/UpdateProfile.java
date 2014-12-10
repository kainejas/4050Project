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
import edu.uga.dawgtrades.model.ObjectModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class UpdateProfile extends HttpServlet{
	private static final long serialVersionUID = 1L;
	static String templateDir = "WEB-INF/templates";
	static String resultTemplateName = "ViewProfile.ftl";
	
	private Configuration cfg;
	
	public void init(){
		cfg = new Configuration();
		cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/templates");
	}
    
    public void doGet( HttpServletRequest req, HttpServletResponse res )
    throws ServletException, IOException
    {
        HttpSession    httpSession = null;
        
        httpSession = req.getSession();
        if( httpSession == null || SessionManager.getSessionById((String)httpSession.getAttribute( "ssid") ) == null ) {
            // not logged in!
            res.sendRedirect("http://uml.cs.uga.edu:8080/team3/login.html");
        }
        else {
            res.sendRedirect("http://uml.cs.uga.edu:8080/team3/UpdateProfile.html");
        }
        
        
    }
    
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		Template resultTemplate = null;
		BufferedWriter toClient = null;
        String first_name = null;
        String last_name = null;
        String user_name = null;
        String password = null;
        String email = null;
        String phone = null;
        long user_id = 0;
		ObjectModel objectModel = null;
		Logic logic = null;
		HttpSession httpSession;
		Session session;
		String ssid;
		
		try{
			resultTemplate = cfg.getTemplate(resultTemplateName);
		}
		catch(IOException e){
			throw new ServletException( "Can't load template in: " + templateDir + ": " + e.toString());
		}
		
		toClient = new BufferedWriter(new OutputStreamWriter(res.getOutputStream(), resultTemplate.getEncoding()));
		
		res.setContentType("text/html; charset=" + resultTemplate.getEncoding());
		
		httpSession = req.getSession();
		if(httpSession == null){
			DawgTradesError.error(cfg, toClient, "Session expired or illegal; please log in");
			return;
		}
		
		ssid = (String) httpSession.getAttribute("ssid");
		if(ssid == null){
			DawgTradesError.error(cfg, toClient, "Session expired or illegal; please log in");
			return;
		}
		
		session = SessionManager.getSessionById(ssid);
		objectModel = session.getObjectModel();
		if(objectModel == null){
			DawgTradesError.error(cfg, toClient, "Session expired or illegal; please log in");
			return;
		}
		
		logic = new LogicImpl(objectModel);
        user_name = session.getUser().getName();
        first_name = req.getParameter("first_name");
        last_name = req.getParameter("last_name");
        password = req.getParameter("password");
        email = req.getParameter("email");
        phone = req.getParameter("phone");
        
		if(first_name == null){
			DawgTradesError.error(cfg, toClient, "Unspecified first name");
			return;
		}
        if(last_name == null){
            DawgTradesError.error(cfg, toClient, "Unspecified last name");
            return;
        }

        if(email == null){
            DawgTradesError.error(cfg, toClient, "Unspecified email");
            return;
        }

        if(phone == null){
            DawgTradesError.error(cfg, toClient, "Unspecified phone");
            return;
        }

		
		try{
			user_id = logic.updateProfile(user_name, first_name, last_name, password, email, phone, true);
		}
		catch(Exception e){
			DawgTradesError.error(cfg, toClient, e);
			return;
		}
		
		Map<String, Object> root = new HashMap<String, Object>();
		
		root.put("user_name", user_name);
		root.put("first_name", first_name);
        root.put("last_name", last_name);
        root.put("email", email);
        root.put("phone", phone);
		
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
