package com.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="POST")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_post")
    private long id;

    @Column(name="titulo")
    private String titulo;

    @JsonIgnore
    @Column(name="contenido")
    private String contenido;

    @Column(name="imagen")
    private String imagen;

    @Column(name = "fecha_creacion")
    private LocalDate fecha;

    @JsonIgnore
    @Column(name = "estado", columnDefinition = "boolean default true")
    private Boolean estado = true;

    @Column(name="id_categoria")
    private long idCategoria;

    @JsonIgnore
    @Column(name="id_usuario")
    private long idUsuario;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name="id_categoria", insertable = false, updatable = false, nullable = false)
    private Categoria categoria;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name="id_usuario", insertable = false, updatable = false, nullable = false)
    private Usuario usuario;

    public Post() {
    }

    

	public Post(String titulo, String contenido, String imagen, LocalDate fecha, Boolean estado, Categoria categoria,
			Usuario usuario) {
		super();
		this.titulo = titulo;
		this.contenido = contenido;
		this.imagen = imagen;
		this.fecha = fecha;
		this.estado = estado;
		this.categoria = categoria;
		this.usuario = usuario;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
    
    
}
