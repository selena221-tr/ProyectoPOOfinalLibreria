package epn.esfot.proyectofinallibreria;

import epn.esfot.proyectofinallibreria.modelo.Cliente;
import epn.esfot.proyectofinallibreria.modelo.ClienteRepository;
import epn.esfot.proyectofinallibreria.modelo.Libro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicioCliente {
  @Autowired
  ClienteRepository clienteRepository;

  public Cliente buscarNombre(String nombre){
    return  clienteRepository.findByNombre(nombre);
  }

  public Cliente registrarCliente(Cliente cR){
    return clienteRepository.save(cR);
  }
}
