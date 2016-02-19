package com.betsol.authentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.internal.util.Base64;

public class Authentication {
	
    public List<String> extractHeaderInfo(ContainerRequestContext requestContext) {

       

        List<String> authorization = requestContext.getHeaders().get("Authorization");
        System.out.println(authorization);
        List<String> usernameAndPassword = new ArrayList<String>();

       
        String usernameAndPasswordFromHeader = new String(Base64.decode(authorization.get(0).replaceFirst("Basic" + " ", "").getBytes()));

        usernameAndPassword.add(0, Arrays.asList(usernameAndPasswordFromHeader.split(":")).get(0));
        usernameAndPassword.add(1, Arrays.asList(usernameAndPasswordFromHeader.split(":")).get(1));

        return usernameAndPassword;
    }
}
