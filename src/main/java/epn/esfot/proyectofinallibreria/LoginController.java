package epn.esfot.proyectofinallibreria;

import epn.esfot.proyectofinallibreria.modelo.Cliente;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

  @FXML
  public void initialize() {
    cmbRol.setItems(FXCollections.observableArrayList(
      "Administrador",
      "Cliente",
      "Invitado"
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

  public void validar() {

    if (txtUser.getText().isEmpty() || pwsPassword.getText().isEmpty() || cmbRol.getValue() == null) {
      mostrarAlerta(Alert.AlertType.ERROR, "Credenciales inválidas", "Debe llenar todos los campos para continuar");
      limpiar();
      return;
    }

    servicioCliente.buscarNombre(txtUser.getText()).ifPresentOrElse(cliente -> {

        if (cliente.getContrasenia().equals(pwsPassword.getText()) && cliente.getRol().equalsIgnoreCase(cmbRol.getValue())) {
          mostrarAlerta(Alert.AlertType.INFORMATION, "Correcto", "Inicio de sesión exitoso");
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
        } else {
          mostrarAlerta(Alert.AlertType.ERROR, "Error", "Contraseña o rol incorrectos");
        }
      }, () -> {
        mostrarAlerta(Alert.AlertType.ERROR, "Error", "El usuario no existe");
      });

  }
}
