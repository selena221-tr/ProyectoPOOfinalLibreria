package epn.esfot.proyectofinallibreria;

import epn.esfot.proyectofinallibreria.modelo.Usuario;
import epn.esfot.proyectofinallibreria.modelo.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioCliente {
  @Autowired
  UsuarioRepository clienteRepository;

  public Usuario buscarNombre(String nombre){
    return  clienteRepository.findByNombre(nombre);
  }

  public Usuario registrarCliente(Usuario cR){
    return clienteRepository.save(cR);
  }
}
