package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import entities.Account;
import entities.User;
import exceptions.MkmException;
import responses.AccountResponse;
import responses.MessageOverviewResponse;
//import responses.MessagesWithUserResponse;
import responses.UserCollectionResponse;
import responses.UserResponse;
import service.AccountService;
import service.ArticleService;
import service.CartServices;
import service.ProductServices;
import service.StockService;
import service.UserService;
import service.WantsService;
//import tools.DisplayLanguage;
import tools.MkmAPIConfig;

public class Main
{

	private static String APP_TOKEN = "85by6ZmgNVeccIIN";
	private static String APP_SECRET = "qIh6JjCa99rLs3O92yepOMop7LyPzvpZ";
	private static String ACCESS_TOKEN = "TqiOs0yzqKvqvm12BvqdZbfQ3JcSushD";
	private static String ACCESS_TOKEN_SECRET = "G82dOchJ2z4qbJi7oVKeKB5pN75MCaTl";

	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException 
	{
		try {
			MkmAPIConfig.getInstance().init(ACCESS_TOKEN_SECRET,ACCESS_TOKEN,APP_SECRET,APP_TOKEN);
		} catch (MkmException e) {
			e.printStackTrace();
		}
		
		UserService userService = UserService.getInstance();
		UserResponse userResponse = userService.getUser("Itaca");
		User itaca = userResponse.getUser();
		
		//AccountService accountService = AccountService.getInstance();
		//AccountResponse aResponse = accountService.getAccountDetails();
		//MessageOverviewResponse moResponse = accountService.getMessageOverview();
		//MessagesWithUserResponse mwu = accountService.getMessagesThreadWithUser(itaca.getIdUser()); 
		//MessagesWithUserResponse response = accountService.getSpecificMessage(itaca.getIdUser(), "5cbee2231335a8545e545506");
		
		//accountService.getUnreadMessages();
		//accountService.redeemCoupons(new ArrayList<String>().add("12345"));
		//AccountResponse a1 = accountService.changeVacationStatus(false, false, false);
		//accountService.changeVacationStatus(true, true, false);
		//accountService.changeVacationStatus(true, true, true);
		//AccountResponse a2 = accountService.changeDisplayLanguage(DisplayLanguage.ENGLISH);
		//accountService.changeDisplayLanguage(DisplayLanguage.FRENCH);
		//accountService.changeDisplayLanguage(DisplayLanguage.GERMAN);
		//accountService.changeDisplayLanguage(DisplayLanguage.SPANISH);
		//accountService.changeDisplayLanguage(DisplayLanguage.ITALIAN);
		
//		UserService userService = UserService.getInstance();
//		UserResponse uResponse = userService.getUser("Itaca");
//		UserCollectionResponse ucResponse = userService.discoverUsers("itaca");
		
		ArticleService articleService = ArticleService.getInstance();
		//articleService.getArticlesFromUser(itaca.getIdUser());
		//articleService.getProducts("Springleaf");
		articleService.getArticles("265535", null);
		
		System.out.println();
	}

}
