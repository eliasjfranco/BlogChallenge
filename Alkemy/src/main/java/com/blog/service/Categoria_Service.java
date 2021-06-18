package com.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.blog.model.Categoria;
import com.blog.repository.ICategoria;
import org.springframework.stereotype.Service;

@Service("categoriaService")
public class Categoria_Service {

	@Autowired
	ICategoria repository;
	
	public void saveCategoria(Categoria categoria) {
		repository.save(categoria);
	}

	public Boolean existByNombre(String nombre) {
		return repository.existsByNombre(nombre);
	}
	
	public Boolean existsById(Long id) {
		return repository.existsById(id);
	}
}
