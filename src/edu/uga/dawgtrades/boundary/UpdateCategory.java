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


public class UpdateCategory extends HttpServlet{
	private static final long serialVersionUID = 1L;
	static String templateDir = "WEB-INF/templates";
	static String resultTemplateName = "DefineCategory-Result.ftl";
	
	private Configuration cfg;
	
	public void init(){
		cfg = new Configuration();
		cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/templates");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		Template resultTemplate = null;
		BufferedWriter toClient = null;
		String category_name = null;
		String parent_id_str;
		long parent_id;
		String category_id_str;
		long category_id = 0;
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
        category_name = req.getParameter("category_name");
        parent_id_str = req.getParameter("parent_id");
        category_id_str = req.getParameter("category_id");
		if(category_name == null){
			DawgTradesError.error(cfg, toClient, "Unspecified category name");
			return;
		}
		
		try{
			parent_id = Long.parseLong(parent_id_str);
		}
		catch(Exception e){
			DawgTradesError.error(cfg, toClient, "parent_id should be a number and is: " + parent_id_str);
			return;
		}
		
		if(parent_id <= 0){
			DawgTradesError.error(cfg, toClient, "Non-positive parent_id: " + parent_id);
			return;
		}
		
		try{
			category_id = Long.parseLong(category_id_str);
		}
		catch(Exception e){
			DawgTradesError.error(cfg, toClient, "category_id should be a number and is: " + parent_id_str);
			return;
		}
		
		if(category_id <= 0){
			DawgTradesError.error(cfg, toClient, "Non-positive category_id: " + category_id);
			return;
		}
		
		try{
			category_id = logic.updateCategory(category_id, category_name, parent_id);
		}
		catch(Exception e){
			DawgTradesError.error(cfg, toClient, e);
			return;
		}
		
		Map<String, Object> root = new HashMap<String, Object>();
		
		root.put("category_name", category_name);
		root.put("category_id", new Long(category_id));
		
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
