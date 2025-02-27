package tools;

public final class Constants {

	private Constants() {}
	
	public static final String MKM_API_VERSION="2.0";
	
	public static final String MKM_ERROR="ERROR";
	public static final String MKM_LOG_LINK="LINK=";
	public static final String MKM_LOG_REQUEST="REQUEST=";
	public static final String MKM_LOG_RESPONSE="RESP=";
	public static final String OAUTH_AUTHORIZATION_HEADER="Authorization";
	public static final String XML_HEADER = "<?xml version='1.0' encoding='UTF-8' ?>";
	public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31";

	public static final String MKM_API = "https://api.cardmarket.com/ws/v"+ Constants.MKM_API_VERSION;
	public static final String MKM_SANDBOX = "";
}
