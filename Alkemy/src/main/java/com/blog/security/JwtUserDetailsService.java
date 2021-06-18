package com.blog.security;

import com.blog.model.Usuario;
import com.blog.repository.IUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuario repository;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByUsername(username);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return new org.springframework.security.core.userdetails.User(usuario.getUsername(), usuario.getPassword(), new ArrayList<>());
    }

    public Usuario save(User user){
        Usuario usuario = new Usuario();
        usuario.setUsername(user.getUsername());
        usuario.setPassword(bcryptEncoder.encode(user.getPwd()));
        return repository.save(usuario);
    }
}
