package com.betsol.authentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.internal.util.Base64;

public class Authentication {
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";

    public List<String> extractHeaderInfo(ContainerRequestContext requestContext) {

       
        MultivaluedMap<String, String> headers = requestContext.getHeaders();

        List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
        List<String> usernameAndPassword = new ArrayList<String>();

        //If no authorization information present; block access
        if (authorization == null || authorization.isEmpty()) {
            return usernameAndPassword;
        } //Decode username and password
        String usernameAndPasswordFromHeader = new String(Base64.decode(authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "").getBytes()));

        usernameAndPassword.add(0, Arrays.asList(usernameAndPasswordFromHeader.split(":")).get(0));
        usernameAndPassword.add(1, Arrays.asList(usernameAndPasswordFromHeader.split(":")).get(1));

        return usernameAndPassword;
    }
}
