package com.blog.service;

import com.blog.model.Post;
import com.blog.repository.IPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("postService")
public class Post_Service {

    @Autowired
    IPost repository;

    public void savePost(Post post){
        repository.save(post);
    }

    public List<Post> getAllPost(){
        return repository.findAllPostActive();
    }

    public List<Post> findAllPostById(Long id){
        return repository.findAllById(id);
    }
    
    public Post findPostById(Long id) {
    	return repository.getById(id);
    }

    public void updatePost(Post post){
        repository.save(post);
    }

    public void deletePost(Long id){
        repository.deleteById(id);
    }

    public Boolean existsById(Long id){
        return repository.existsById(id);
    }

    public List<Post> findByTitulo(String titulo){
        return repository.findByTitulo(titulo);
    }

    public List<Post> findByIdGenero(long id){
        return repository.findByIdCategoria(id);
    }
    
    public Boolean existsByImagen(String imagen) {
    	return repository.existsByImagen(imagen);
    }
    
    public List<Post> findByTituloAndCategoria(String titulo, Long categoria){
    	return repository.obtenerByIdAndCategoria(titulo, categoria);
    }



}
