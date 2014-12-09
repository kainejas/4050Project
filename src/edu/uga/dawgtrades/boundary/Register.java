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


public class Register extends HttpServlet{
	private static final long serialVersionUID = 1L;
	static String templateDir = "WEB-INF/templates";
	static String resultTemplateName = "MainWindow.ftl";

	private Configuration cfg;

	public void init(){
		cfg = new Configuration();
		cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/templates");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		Template resultTemplate = null;
		BufferedWriter toClient = null;
		String user_name = null;
		String password = null;
		String is_admin_str = null;
		boolean is_admin = false;
		String first_name = null;
		String last_name = null;
		String address = null;
		String phone = null;
		String email = null;
		String can_text_str = null;
		boolean can_text = false;
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

		ssid = (String) httpSession.getAttribute("ssid");


		session = SessionManager.getSessionById(ssid);
		objectModel = session.getObjectModel();
        if(objectModel == null){
			DawgTradesError.error(cfg, toClient, "Object model null.");
			return;
		}

		logic = new LogicImpl(objectModel);

		user_name = req.getParameter("user_name");
		password = req.getParameter("password");
		//is_admin_str = req.getParameter("is_admin");
		email = req.getParameter("email");
		first_name = req.getParameter("first_name");
		last_name = req.getParameter("last_name");
		phone = req.getParameter("phone");
		can_text_str = req.getParameter("can_text");

		if(user_name == null || user_name.equals("")){
			DawgTradesError.error(cfg, toClient, "Unspecified user name");
			return;
		}

		if(password == null || password.equals("")){
			DawgTradesError.error(cfg, toClient, "Unspecified password");
			return;
		}
		
		try{
			is_admin = Boolean.parseBoolean(is_admin_str);
		}
		catch(Exception e){
			DawgTradesError.error(cfg, toClient, "Invalid admin status: " + is_admin_str);
			return;
		}

		if( first_name == null || first_name.equals("") ) {
			DawgTradesError.error( cfg, toClient, "Unspecified first name" );
			return;
		}

		if( last_name == null || last_name.equals("")) {
			DawgTradesError.error( cfg, toClient, "Unspecified last name" );
			return;
		}

		if( phone == null || phone.equals("") )
			phone = "";

		if( email == null  || email.equals("")) {
			DawgTradesError.error( cfg, toClient, "Unspecified email" );
			return;
		}
		/*
		try{
			//is_admin = Boolean.parseBoolean(can_text_str);
		}
		catch(Exception e){
			DawgTradesError.error(cfg, toClient, "Invalid text status: " + is_admin_str);
			return;
		}
        */
		
		try{
			user_id = logic.register(user_name, first_name, last_name, password, is_admin, email, phone, can_text);
		}
		catch(Exception e){
			DawgTradesError.error(cfg, toClient, e);
			return;
		}
        
        try {
            ssid = SessionManager.login( user_name, password );
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

		Map<String, Object> root = new HashMap<String, Object>();

		root.put("first_name", first_name);
		root.put("last_name", last_name);
		root.put("user_id", user_id);

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
