package com.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service("auth_service")
public class AutenticacionService {

    @Autowired
    private AuthenticationManager authenticationManager;

    public void autenticacion(String username, String pwd) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,pwd));
        }catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
