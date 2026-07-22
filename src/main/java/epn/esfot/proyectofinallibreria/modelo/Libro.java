package epn.esfot.proyectofinallibreria.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, length = 100)
  private String titulo;

  @Column(nullable = false, length = 100)
  private String autor;

  @Column(nullable = false, length = 100)
  private String editorial;

  @Column(nullable = false, length = 100)
  private String formato;

  @Column( nullable = false)
  private int anio_publicacion;

  @Column( nullable = false)
  private int numero_paginas;


  @Column(nullable = false, length = 100)
  private String categoria;

  @ManyToOne
  @JoinColumn(name="usuario_id")
  private Usuario usuario;


  public Libro() {
  }

  public Libro(Integer id, String titulo, String autor, String editorial, String formato, int anio_publicacion, int numero_paginas, String categoria) {
    this.id = id;
    this.titulo = titulo;
    this.autor = autor;
    this.editorial = editorial;
    this.formato = formato;
    this.anio_publicacion = anio_publicacion;
    this.numero_paginas = numero_paginas;
    this.categoria = categoria;
  }

  public Libro(String titulo, String autor, String editorial, String formato, int anio_publicacion, int numero_paginas, String categoria) {
    this.titulo = titulo;
    this.autor = autor;
    this.editorial = editorial;
    this.formato = formato;
    this.anio_publicacion = anio_publicacion;
    this.numero_paginas = numero_paginas;
    this.categoria = categoria;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getAutor() {
    return autor;
  }

  public void setAutor(String autor) {
    this.autor = autor;
  }

  public String getEditorial() {
    return editorial;
  }

  public void setEditorial(String editorial) {
    this.editorial = editorial;
  }

  public String getFormato() {
    return formato;
  }

  public void setFormato(String formato) {
    this.formato = formato;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public int getAnio_publicacion() {
    return anio_publicacion;
  }

  public void setAnio_publicacion(int anio_publicacion) {
    this.anio_publicacion = anio_publicacion;
  }

  public int getNumero_paginas() {
    return numero_paginas;
  }

  public void setNumero_paginas(int numero_paginas) {
    this.numero_paginas = numero_paginas;
  }


  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  @Override
  public String toString() {
    return "Libro{" +
      "id=" + id +
      ", titulo='" + titulo + '\'' +
      ", autor='" + autor + '\'' +
      ", editorial='" + editorial + '\'' +
      ", formato='" + formato + '\'' +
      ", categoria='" + categoria + '\'' +
      ", anioPublicacion=" + anio_publicacion +
      ", numeroPaginas=" + numero_paginas +
      '}';
  }
}
