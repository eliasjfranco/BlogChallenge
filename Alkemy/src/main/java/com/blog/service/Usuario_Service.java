package com.blog.service;

import com.blog.model.Usuario;
import com.blog.repository.IUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class Usuario_Service {

    @Autowired
    IUsuario repository;

    public List<Usuario> getAllUsuario(){
        return repository.findAll();
    }

    public void saveUsuario(Usuario usuario){
        repository.save(usuario);
    }

    public Usuario findUsuario(Long id){
        return repository.getById(id);
    }

    public void updateUsuario(Usuario usuario){
        repository.save(usuario);
    }

    public void deleteUsuario(Long id){
        repository.deleteById(id);
    }

    public Boolean existByUsername(String username){
        return repository.existsByUsername(username);
    }
    
    public Usuario findByUsername(String username) {
    	return repository.findByUsername(username);
    }
}
