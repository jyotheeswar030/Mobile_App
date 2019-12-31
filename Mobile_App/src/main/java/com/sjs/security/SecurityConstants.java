package com.sjs.security;

import com.sjs.SpringApplicationContext;

public class SecurityConstants {
	public static final long EXPRITION_TIME = 864000000;// 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";
//public static final String TOKEN_SECRET ="jf9ijgu83nfl0";

	public static String getTokenSecret() {
		AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
		return appProperties.getTokenSecret();
	}
}
