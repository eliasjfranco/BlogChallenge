package com.blog.controller;

import com.blog.jwt.JwtResponse;
import com.blog.jwt.JwtTokenUtil;
import com.blog.security.JwtUserDetailsService;
import com.blog.security.User;
import com.blog.service.AutenticacionService;
import com.blog.service.Usuario_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller_Auth {

    @Autowired
    private AutenticacionService auth;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    Usuario_Service usuario_service;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value="/auth/login", method = RequestMethod.POST)
    public ResponseEntity<?> crearAutenticacion(@RequestBody User user) throws Exception{
        auth.autenticacion(user.getUsername(), user.getPwd());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

    @RequestMapping(value = "/auth/sing_up", method = RequestMethod.POST)
    public ResponseEntity<?> guardarUsuario(@RequestBody User user){
        if(usuario_service.existByUsername(user.getUsername())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(userDetailsService.save(user));
        }
    }

}
