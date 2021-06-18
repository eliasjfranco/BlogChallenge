package com.blog.controller;

import com.blog.jwt.JwtTokenUtil;
import com.blog.model.Categoria;
import com.blog.model.Mensaje;
import com.blog.model.Post;
import com.blog.model.Usuario;
import com.blog.service.Categoria_Service;
import com.blog.service.Post_Service;
import com.blog.service.Usuario_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class Controller_Endpoint {
    @Resource(name="postService")
    Post_Service postService;

    @Resource(name="userService")
    Usuario_Service usuarioService;
    
    @Resource(name="categoriaService")
    Categoria_Service categoriaService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping(value = "/posts")
    public ResponseEntity<?> getAllPosts(@RequestParam(value ="title", required = false) String titulo,
                                         @RequestParam(value = "category", required = false, defaultValue = "0") long categoria,
                                         @RequestParam(value = "id", required = false, defaultValue = "0") long id){
        List<Post> post;
        post = searchPosts(titulo,categoria, id);
        if(post.isEmpty()) {
        	return new ResponseEntity<>(new Mensaje("No se ha encontrado, verifique los datos ingresados!"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(post);
    }


    @PostMapping(value="/posts")
    public ResponseEntity<?> savePosts(@RequestBody Post post, HttpServletRequest request){
        post.setIdUsuario(obtenerIdUsuario(request)); //Obtiene el id de usuario a travez del token
        post.setImagen(controlUrlImagen(post.getImagen())); //Controla url si es de una imagen
        post.setImagen(controlImagen(post.getImagen())); //Controla si ya no se encuentra guardada la imagen
        
        if(!categoriaService.existsById(post.getIdCategoria())) {
        	return new ResponseEntity<>(new Mensaje("No se ha guardado el post, verifique que exista la categoria ingresada"), HttpStatus.BAD_REQUEST);
        }
        postService.savePost(post);
        return new ResponseEntity<>(new Mensaje("Post guardado con exito"), HttpStatus.OK);
    }

    @PatchMapping(value="/posts")
    public ResponseEntity<?> updatePosts(@RequestBody Post post, @RequestParam(value = "id", required = true) long id){
        if(!postService.existsById(id)){
            return new ResponseEntity<>(new Mensaje("El Post especificado no existe!"), HttpStatus.BAD_REQUEST);
        }
        else{
        	post.setImagen(controlUrlImagen(post.getImagen())); 
        	post.setImagen(controlImagen(post.getImagen()));
            postService.savePost(post);
            return ResponseEntity.ok(new Mensaje("Post actualizado con exito!"));
        }
    }

    @DeleteMapping(value = "/posts")
    public ResponseEntity<?> deletePots(@RequestParam(value = "id", required = true) long id){
        if(postService.existsById(id)){
            cambiarEstado(id); 
            return new ResponseEntity<>(new Mensaje("Post eliminado con exito!"), HttpStatus.OK);
        }else
            return new ResponseEntity<>(new Mensaje("No existe el Post especificado"), HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping(value = "/category")
    public ResponseEntity<?> addCategory(@RequestBody Categoria categoria){
    	if(!categoriaService.existByNombre(categoria.getNombre())) {
    		categoriaService.saveCategoria(categoria);
    		return new ResponseEntity<>(new Mensaje("Categoria guardada!"), HttpStatus.OK);
    	}else
    		return new ResponseEntity<>(new Mensaje("Ya existe la categoria ha guardar"), HttpStatus.BAD_REQUEST);
    	
    }
    
    
    //Verifica si ya esta guardada la url de imagen
    public String controlImagen(String imagen) {
    	if(postService.existsByImagen(imagen)) {
    		imagen = "";
    	}
    	return imagen;
    }

    //Verifica la extension de la url, si termina en jpg o png
    public String controlUrlImagen(String imagen){
    	if(imagen.length()>0) {
    		String extension = imagen.substring(imagen.length() - 4);
    		System.out.println(extension);
        	if(!extension.equals(".jpg") && !extension.equals(".png")) {
        		imagen = "";
        	}
    	}
    	
    	return imagen;
    }

    //Busqueda de Posts
    public List<Post> searchPosts(String titulo, long categoria, long id){
        List<Post> post = new ArrayList<Post>();
        if(titulo!=null || id != 0 || categoria !=0) {
        	
        	//Busca por titulo e id_categoria
        	if(titulo != null && categoria != 0) {
        		post = postService.findByTituloAndCategoria(titulo, categoria);
        	}else {
        		if(titulo != null)
        			post = postService.findByTitulo(titulo);
        		if(id > 0)
        			post = postService.findAllPostById(id);
        		if(categoria > 0)
        			post = postService.findByIdGenero(categoria);
        	}
        }else
             post = postService.getAllPost();
        return post;
    }

    //Obtener id usuario logeado
    public long obtenerIdUsuario(HttpServletRequest request){
        String auth = request.getHeader("Authorization");
        String token = auth.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Usuario user = new Usuario();
        user = usuarioService.findByUsername(username);
        return user.getId();
    }

    //Cambia estado de post
    public void cambiarEstado(long id){
    	Post post = new Post();
    	post = postService.findPostById(id);
    	post.setEstado(false);
    	postService.savePost(post);
    }
}