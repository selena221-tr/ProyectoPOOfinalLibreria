package epn.esfot.proyectofinallibreria.modelo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private  Integer id;
  private String nombre;
  private String contrasenia;
  private String rol;

  @OneToMany(mappedBy="usuario")
  private List<Libro> librosReservados = new ArrayList<>();

  public Usuario() {
  }

  public Usuario(Integer id, String nombre, String contrasenia, String rol) {
    this.id = id;
    this.nombre = nombre;
    this.contrasenia = contrasenia;
    this.rol = rol;
  }
  public Usuario(String nombre,  String rol) {
    this.nombre = nombre;
    this.rol = rol;
  }

  public Usuario(String nombre, String contrasenia, String rol) {
    this.nombre = nombre;
    this.contrasenia = contrasenia;
    this.rol = rol;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getContrasenia() {
    return contrasenia;
  }

  public void setContrasenia(String contrasenia) {
    this.contrasenia = contrasenia;
  }

  public String getRol() {
    return rol;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }

  @Override
  public String toString() {
    return "Cliente{" +
      "id=" + id +
      ", nombre='" + nombre + '\'' +
      ", contrasenia='" + contrasenia + '\'' +
      ", rol='" + rol + '\'' +
      '}';
  }
}
