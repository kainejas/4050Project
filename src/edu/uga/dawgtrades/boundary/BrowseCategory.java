package edu.uga.dawgtrades.boundary;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;

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
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.Item;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class BrowseCategory extends HttpServlet{
	private static final long serialVersionUID = 1L;
	static String templateDir = "WEB-INF/templates";
	static String resultTemplateName = “BrowseCategory.ftl";
	
	private Configuration cfg;
	
	public void init(){
		cfg = new Configuration();
		cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/templates");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		Template resultTemplate = null;
		BufferedWriter toClient = null;
		String category_name = null;
		List<Category> rc = null;
		List<List<Object>> categories = null;
		List<Object> category = null;
		Category c = null;
		List<Item> ri = null;
		List<List<Object>> items = null;
		List<Object> item = null;
		Item i = null;
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
		
		if(category_name == null){
			DawgTradesError.error(cfg, toClient, "Unspecified category name");
			return;
		}
		
		Map<String, Object> root = new HashMap<String, Object>();
		
		try{
			rc = logic.browseCategory(category_name);
		}
		catch(Exception e){
			DawgTradesError.error(cfg, toClient, e);
		}
			
		if(rc == null){
			try{
				ri = logic.browseCategoryItems(category_name);
			}
			catch(Exception e){
				DawgTradesError.error(cfg, toClient, e);
				return;
			}
		}

		if (rc == null) {
			for (int i = 0; i < ri.size(); i++) {
				root.put(“item” + i + “_id”,ri.get(i).getId();
				root.put(“item” + i + “_name”,ri.get(i).getName();
			}
		} else {
			for (int i = 0; i < ri.size(); i++)
				root.put(“category” + i + “_name“,rc.get(i).getName();
		}
		
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
