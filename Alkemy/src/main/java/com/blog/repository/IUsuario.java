package com.blog.repository;

import com.blog.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuario extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    boolean existsByUsername(String username);
}
