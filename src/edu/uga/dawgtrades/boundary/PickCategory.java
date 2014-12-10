package edu.uga.dawgtrades.boundary;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

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
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Category;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class PickCategory extends HttpServlet{
	private static final long serialVersionUID = 1L;
	static String templateDir = "WEB-INF/templates";
	static String resultTemplateName = "AuctionItem.ftl";
	
	private Configuration cfg;
	
	public void init(){
		cfg = new Configuration();
		cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/templates");
	}
    
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        Template resultTemplate = null;
        BufferedWriter toClient = null;
        String item_name = null;
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
        
        String category_name = req.getParameter("category_name");
 
        
        Category modelCat = objectModel.createCategory();
        Category category = null;
        modelCat.setName(category_name);
        try {
        category = objectModel.findCategory(modelCat).next();
        }
        catch(Exception e) {
            DawgTradesError.error( cfg, toClient, "Error findCategory in PickCategory.java  " + e.getStackTrace());
            return;
        }

        
        Iterator<AttributeType> attrTypeIter = null;
        
        
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("user_name", session.getUser().getName());
        if(category_name != null && category != null) {
            root.put("category", category_name);
            
        List<Map<String,String>> tempList = new ArrayList<Map<String, String>>();
        HashMap<String, String> attributeTypeMap = new HashMap<String, String>();
        
        try {
            attrTypeIter = objectModel.getAttributeType(category);
           
            while(attrTypeIter.hasNext()) {
                AttributeType attributeType = attrTypeIter.next();
                attributeTypeMap = new HashMap<String, String>();
                attributeTypeMap.put("name",attributeType.getName());
                tempList.add(attributeTypeMap);
                
            }
            
            root.put("attribute_types", tempList);
        }
        catch(Exception e) {
            DawgTradesError.error( cfg, toClient, "Error making tempList for AuctionItem Page   " + e.getStackTrace() );
            return;
        }
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
