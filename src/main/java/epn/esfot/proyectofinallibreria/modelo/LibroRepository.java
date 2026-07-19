package epn.esfot.proyectofinallibreria.modelo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {
  Optional<Libro> findByTitulo(String titulo);

}
