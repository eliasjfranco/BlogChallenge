package com.blog.repository;

import com.blog.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoria extends JpaRepository<Categoria, Long> {
	
	Boolean existsByNombre(String nombre);
}
