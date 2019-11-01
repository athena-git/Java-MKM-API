package service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import entities.Article;
import entities.Link;
import entities.Product;
import entities.Response;
import entities.User;
import entities.Article.ARTICLES_ATT;
import exceptions.MkmNetworkException;
import tools.MkmAPIConfig;
import tools.MkmConstants;
import tools.Tools;

public class ArticleService {
	private Logger logger = LogManager.getLogger(this.getClass());
	private AuthenticationServices auth;
	private XStream xstream;
	
	public ArticleService() {
		auth=MkmAPIConfig.getInstance().getAuthenticator();
		
		xstream = new XStream(new StaxDriver());
			XStream.setupDefaultSecurity(xstream);
	 		xstream.addPermission(AnyTypePermission.ANY);
	 		xstream.alias("response", Response.class);
	 		xstream.addImplicitCollection(Response.class,"article", Article.class);
	 		xstream.addImplicitCollection(Response.class,"links",Link.class);
	 		xstream.ignoreUnknownElements();
	}
	
	public List<Article> find(User u,Map<ARTICLES_ATT,String> atts) throws IOException 
	{
		String link = MkmConstants.MKM_API_URL+"/users/"+u.getUsername()+"/articles";
		logger.debug(MkmConstants.MKM_LOG_LINK+link);
		
		if(atts!=null && atts.size()>0)
	    	{
	    		link+="?";
	    		List<String> paramStrings = new ArrayList<>();
	 	        for(Entry<ARTICLES_ATT, String> parameter:atts.entrySet())
		             paramStrings.add(parameter.getKey() + "=" + parameter.getValue());
		        
	 	        link+=Tools.join(paramStrings, "&");
	    	}
		
		 HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
         connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"GET")) ;
         connection.connect() ;
         MkmAPIConfig.getInstance().updateCount(connection);
     	boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
     	if(!ret)
     		throw new MkmNetworkException(connection.getResponseCode());
         
         
         String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
         logger.debug(MkmConstants.MKM_LOG_RESPONSE+xml);
  	   
         Response res = (Response)xstream.fromXML(xml);
		
     	if(isEmpty(res.getArticle()))
    		return new ArrayList<>();
    
         
         
		return res.getArticle();
	}
	
	public List<Article> find(String userName, Map<ARTICLES_ATT, String> atts) throws MalformedURLException, IOException
	{
		String link = MkmConstants.MKM_API_URL+"/users/"+userName+"/articles";
		logger.debug(MkmConstants.MKM_LOG_LINK+link);
		
		if(atts!=null && atts.size()>0)
    	{
    		link+="?";
    		List<String> paramStrings = new ArrayList<>();
 	        for(Entry<ARTICLES_ATT, String> parameter: atts.entrySet())
	             paramStrings.add(parameter.getKey() + "=" + parameter.getValue());
	        
 	        link+=Tools.join(paramStrings, "&");
    	}
		
		 HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
		 connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"GET")) ;
		 connection.connect() ;
		 MkmAPIConfig.getInstance().updateCount(connection);
		 
		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
     	if(!ret)
     		throw new MkmNetworkException(connection.getResponseCode());
         
         
         String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
         logger.debug(MkmConstants.MKM_LOG_RESPONSE+xml);
  	   
         Response res = (Response)xstream.fromXML(xml);
		
     	if(isEmpty(res.getArticle()))
    		return new ArrayList<>();
     	
		return res.getArticle();
	}
	
	
	private boolean isEmpty(List<Article> article) {
		
		return (article.get(0).getIdArticle()==0);
	}

	public List<Article> find(Product p,Map<ARTICLES_ATT,String> atts) throws IOException 
	{
		String link = MkmConstants.MKM_API_URL+"/articles/"+p.getIdProduct();
    	logger.debug(MkmConstants.MKM_LOG_LINK+link);
    	
    	if(atts!=null && atts.size()>0)
	    	{
	    		link+="?";
	    		List<String> paramStrings = new ArrayList<>();
	    		 for(Entry<ARTICLES_ATT, String> parameter:atts.entrySet())
		             paramStrings.add(parameter.getKey() + "=" + parameter.getValue());
		        
	 	        link+=Tools.join(paramStrings, "&");
	    	}
    	logger.debug(MkmConstants.MKM_LOG_LINK+link);
    	
	    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
			               MkmAPIConfig.getInstance().updateCount(connection);
			               
		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
	 	if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());
			         	               
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		logger.debug(MkmConstants.MKM_LOG_RESPONSE+xml);
		  	  
		Response res = (Response)xstream.fromXML(xml);
		
		for(Article a : res.getArticle())
			a.setProduct(p);
		
		return res.getArticle();
	}
	
}
