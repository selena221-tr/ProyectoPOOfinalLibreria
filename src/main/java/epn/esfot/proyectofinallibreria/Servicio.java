package epn.esfot.proyectofinallibreria;

import epn.esfot.proyectofinallibreria.modelo.Libro;
import epn.esfot.proyectofinallibreria.modelo.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Servicio {
  @Autowired
  public LibroRepository libroRepository;

  public List<Libro> listarTodos(){
    return libroRepository.findAll();
  }

  public Optional<Libro> buscarTitulo(String titulo){
    return  libroRepository.findByTitulo(titulo);
  }

  public Libro insertar(Libro libro){
    return libroRepository.save(libro);
  }
  public Libro actualizar(Libro li){
    return  libroRepository.save(li);
  }
  public void eliminar(Integer id){
    libroRepository.deleteById(id);
  }






}
