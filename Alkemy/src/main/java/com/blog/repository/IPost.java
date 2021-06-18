package com.blog.repository;

import com.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPost extends JpaRepository <Post, Long> {

    @Query(value = "SELECT * from POST p where p.estado = true order by fecha_creacion desc", nativeQuery = true)
    List<Post> findAllPostActive();
    
    @Query(value = "SELECT * from POST p where p.estado = true and titulo = :titulo and id_categoria = :categoria", nativeQuery = true)
    List<Post> obtenerByIdAndCategoria(String titulo, Long categoria);

    List<Post> findByOrderByFechaDesc();

    List<Post> findByTitulo(String titulo);

    List<Post> findByIdCategoria(long id);
    
    Boolean existsByImagen(String imagen);
    
    List<Post> findAllById(long id);
}
