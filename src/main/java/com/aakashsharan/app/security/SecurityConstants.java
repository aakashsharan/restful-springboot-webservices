package com.aakashsharan.app.security;

import com.aakashsharan.app.SpringApplicationContext;

public class SecurityConstants {

    public static final long EXPIRATION_DATE = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer";

    public static String getTokenSecret(){
        ApplicationProperties applicationProperties = (ApplicationProperties) SpringApplicationContext.getBean("ApplicationProperties");
        return applicationProperties.getTokenSecret();
    }
}
