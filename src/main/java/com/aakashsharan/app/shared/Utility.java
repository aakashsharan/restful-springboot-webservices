package com.aakashsharan.app.shared;

import com.aakashsharan.app.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Service
public class Utility {

    private final Random RANDOM = new SecureRandom();
    private final String STRING_VALUE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String generateUserId(int length){
        return generateRandomString(length);
    }

    private String generateRandomString(int length){
        StringBuilder randomString = new StringBuilder(length);
        for(int i = 0; i < length; i++){
            randomString.append(STRING_VALUE.charAt(RANDOM.nextInt(STRING_VALUE.length())));
        }
        return randomString.toString();
    }

    public String generateEmailVerificationToken(String userId){
        String token = Jwts.builder().setSubject(userId).setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_DATE))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();
        return token;
    }
}
