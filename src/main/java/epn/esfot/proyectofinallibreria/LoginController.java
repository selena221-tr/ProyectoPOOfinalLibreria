package epn.esfot.proyectofinallibreria;

import epn.esfot.proyectofinallibreria.modelo.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;


import java.io.IOException;

@Controller
public class LoginController {
  @Autowired
  ServicioCliente servicioCliente;
  @Autowired
  private ApplicationContext context;


  @FXML ComboBox<String> cmbRol;
  @FXML TextField txtUser;
  @FXML PasswordField pwsPassword;
  @FXML Button btnRegistrarse;
  @FXML Button btnLogin;

  public static Usuario usuarioregistrado= new Usuario();


  @FXML
  public void initialize() {
    cmbRol.setItems(FXCollections.observableArrayList(
      "Administrador",
      "Cliente"
    ));
  }
  public  void mostrarAlerta(Alert.AlertType alert, String titulo, String mensaje ){
    Alert a= new Alert(alert);
    a.setTitle(titulo);
    a.setContentText(mensaje);
    a.showAndWait();
  }

  @FXML
  public void limpiar(){
    txtUser.setText("");
    pwsPassword.setText("");
    cmbRol.setValue(null);
  }

  public  void abrirVentana(){
    try {
      FXMLLoader loader = new FXMLLoader(
              getClass().getResource("libreria.fxml")
      );
      loader.setControllerFactory(context::getBean);
      Parent root = loader.load();

      Stage stage = new Stage();
      stage.setTitle("Gestión de Libros");
      stage.setScene(new Scene(root));
      stage.show();

      Stage ventanaActual = (Stage) txtUser.getScene().getWindow();
      ventanaActual.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void iniciarSesionInvitado(){
      mostrarAlerta(Alert.AlertType.INFORMATION, "Login", "Usted está ingresando al sistema como invitado");
      usuarioregistrado = new Usuario();
      usuarioregistrado.setRol("Invitado");
      usuarioregistrado.setNombre("Invitado");
      abrirVentana();
  }

  public void validar() {
      if (txtUser.getText().isEmpty() || pwsPassword.getText().isEmpty() || cmbRol.getValue() == null) {
        mostrarAlerta(Alert.AlertType.ERROR, "Credenciales inválidas", "Debe llenar todos los campos para continuar");
        limpiar();
        return;
      }

      Usuario cEncontrado = servicioCliente.buscarNombre(txtUser.getText());

      if (cEncontrado != null
              && BCrypt.checkpw(pwsPassword.getText(), cEncontrado.getContrasenia())
              && cEncontrado.getRol().equalsIgnoreCase(cmbRol.getValue())
      ) {
        usuarioregistrado= cEncontrado;

        mostrarAlerta(Alert.AlertType.INFORMATION, "Correcto", "Inicio de sesión exitoso");
        abrirVentana();
      } else {
        mostrarAlerta(Alert.AlertType.ERROR, "Error", "Contraseña o rol incorrectos");
      }

  }

  @FXML
  public void registrarUsuario(){
    if (!txtUser.getText().isEmpty() && !pwsPassword.getText().isEmpty() && !(cmbRol.getValue() == null)) {
      if (!cmbRol.getValue().equals("Administrador")){
        Usuario cI = new Usuario(
                txtUser.getText(),
                BCrypt.hashpw(pwsPassword.getText(), BCrypt.gensalt()),
                cmbRol.getValue()
        );

        servicioCliente.registrarCliente(cI);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("USUARIO NUEVO");
        alert.setHeaderText("Validando el registro del nuevo usuario...");
        alert.setContentText("Usuario registrado correctamente!");
        alert.show();
      } else{
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR ROL ADMINISTRADOR");
        alert.setHeaderText("Error, no se pueden registrar usuarios con el rol 'Administrador'!");
        alert.setContentText("Verifica el rol e intentalo de nuevo");
        alert.show();
      }
    } else{
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("ERROR CAMPOS VACIOS");
      alert.setHeaderText("Error, no se pueden registrar usuarios sin datos!");
      alert.setContentText("Verifica que todos los campos esten llenos e intentalo de nuevo");
      alert.show();
    }
  }
}
