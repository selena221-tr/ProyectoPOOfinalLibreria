package epn.esfot.proyectofinallibreria;

import epn.esfot.proyectofinallibreria.modelo.Libro;
import epn.esfot.proyectofinallibreria.modelo.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import static epn.esfot.proyectofinallibreria.LoginController.usuarioregistrado;

@Controller
public class LibroController {
  @FXML private TextField txtTitle;
  @FXML private TextField txtAutor;
  @FXML private TextField txtEditorial;
  @FXML private ComboBox cmbFormat;
  @FXML private TextField txtYear;
  @FXML private TextField txtPag;
  @FXML private ComboBox cmbCategory;
  @FXML private RadioButton rbDisponible;
  @FXML private TextField txtTituloBuscado;
  @FXML private TableView<Libro> tblLibros;
  @FXML private TableColumn<Libro, Integer> colId;
  @FXML private TableColumn<Libro, String> colTitulo;
  @FXML private TableColumn<Libro, String> colAutor;
  @FXML private TableColumn<Libro, String> colEditorial;
  @FXML private TableColumn<Libro, Integer> colAnio;
  @FXML private TableColumn<Libro, Integer> colPaginas;
  @FXML private TableColumn<Libro, String> colFormato;
  @FXML private TableColumn<Libro, String> colCategoria;
  @FXML private TableColumn<Libro, String> colUsuario;
  @FXML private Button btnActualizar;
  @FXML private Button btnEliminar;
  @FXML private Button btnAgregar;
  @FXML private Button btnReservar;
  @FXML private TextField txtCliente;

  @Autowired
  private Servicio servicio;
  private Libro libroSeleccionado;

  @Autowired
  private ApplicationContext context;


  @FXML
  public void initialize(){

    if (usuarioregistrado.getRol().equals("Invitado")){
        btnEliminar.setVisible(false);
        btnActualizar.setVisible(false);
        btnReservar.setVisible(false);
        btnAgregar.setVisible(false);
        txtPag.setDisable(true);
        txtYear.setDisable(true);
        txtAutor.setDisable(true);
        txtEditorial.setDisable(true);
        txtTitle.setDisable(true);
        rbDisponible.setDisable(true);
        cmbFormat.setDisable(true);
        cmbCategory.setDisable(true);
        colUsuario.setVisible(false);
    }

    if(usuarioregistrado.getRol().equals("Cliente")){
      btnEliminar.setVisible(false);
      btnActualizar.setVisible(false);
      btnAgregar.setVisible(false);
      txtPag.setDisable(true);
      txtYear.setDisable(true);
      txtAutor.setDisable(true);
      txtEditorial.setDisable(true);
      txtTitle.setDisable(true);
      rbDisponible.setDisable(true);
      cmbFormat.setDisable(true);
      cmbCategory.setDisable(true);
      colUsuario.setVisible(false);
    }

    if(usuarioregistrado.getRol().equals("Administrador")){
      btnReservar.setVisible(false);
    }

    cmbCategory.setItems(FXCollections.observableArrayList(
      "Novela",
      "Ciencia",
      "Historia",
      "Tecnología",
      "Fantasía",
      "Terror",
      "Romance",
      "Biografía",
      "Infantil",
      "Educación"
    ));

    cmbFormat.setItems(FXCollections.observableArrayList(
      "Físico",
      "Digital",
      "Audiolibro"
    ));

    colId.setCellValueFactory(new PropertyValueFactory<>("id"));
    colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
    colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
    colEditorial.setCellValueFactory(new PropertyValueFactory<>("editorial"));
    colAnio.setCellValueFactory(new PropertyValueFactory<>("anio_publicacion"));
    colPaginas.setCellValueFactory(new PropertyValueFactory<>("numero_paginas"));
    colUsuario.setCellValueFactory(data -> {
      Libro libro = data.getValue();
      if(libro.getUsuario()!=null){
        return new SimpleStringProperty(
                libro.getUsuario().getNombre()
        );
      }
      return new SimpleStringProperty("Disponible");
    });
    colFormato.setCellValueFactory(new PropertyValueFactory<>("formato"));
    colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

    tblLibros.setItems(FXCollections.observableArrayList(servicio.listarTodos()));


    tblLibros.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
      if (newSel != null) {
        libroSeleccionado= newSel;
        txtTitle.setText(String.valueOf(newSel.getTitulo()));
        txtAutor.setText(newSel.getAutor());
        txtEditorial.setText(newSel.getEditorial());
        txtYear.setText(String.valueOf(newSel.getAnio_publicacion()));
        txtPag.setText(String.valueOf(newSel.getNumero_paginas()));
        cmbCategory.setValue(newSel.getCategoria());
        cmbFormat.setValue(newSel.getFormato());
        rbDisponible.setSelected(newSel.getUsuario() == null);
      }
    });
  }


  public void cargarLibroEnFormulario(Libro l){
    txtTitle.setText(String.valueOf(l.getTitulo()));
    txtAutor.setText(l.getAutor());
    txtEditorial.setText(l.getEditorial());
    txtYear.setText(String.valueOf(l.getAnio_publicacion()));
    txtPag.setText(String.valueOf(l.getNumero_paginas()));
    cmbCategory.setValue(l.getCategoria());
    cmbFormat.setValue(l.getFormato());
    rbDisponible.setSelected(l.getUsuario() == null);
  }

  public  void mostrarAlerta(Alert.AlertType alert, String titulo, String mensaje ){
    Alert a= new Alert(alert);
    a.setTitle(titulo);
    a.setContentText(mensaje);
    a.showAndWait();
  }

  public boolean confirmar(String titulo, String mensaje) {
    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(mensaje);
    Optional<ButtonType> respuesta = alerta.showAndWait();
    return respuesta.isPresent() && respuesta.get() == ButtonType.OK;
  }

  @FXML
  public void limpiar(){
    txtTitle.setText("");
    txtAutor.setText("");
    txtEditorial.setText("");
    txtYear.setText("");
    txtPag.setText("");
    cmbCategory.setValue(null);
    cmbFormat.setValue(null);
    rbDisponible.setSelected(false);
    txtTituloBuscado.setText("");
  }

  @FXML
  public void listarTodos(){
    tblLibros.setItems(FXCollections.observableArrayList(servicio.listarTodos()));
    limpiar();
  }

  @FXML
  public void buscarTitulo(){
    String t= txtTituloBuscado.getText();
    servicio.buscarTitulo(t).ifPresent(this::cargarLibroEnFormulario);
    tblLibros.getItems().clear();
    servicio.buscarTitulo(t).ifPresent(libro -> {
      tblLibros.getItems().add(libro);
      rbDisponible.setSelected(libro.getUsuario() == null);
    });
  }

  @FXML
  public void crearLibro(){
    if (txtTitle.getText().isBlank() || txtAutor.getText().isBlank() || txtEditorial.getText().isBlank() || txtYear.getText().isBlank() || txtPag.getText().isBlank() || cmbCategory.getValue() == null || cmbFormat.getValue() == null) {
      mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Debe completar todos los campos.");
      return;
    }
    try {
      String titulo = txtTitle.getText();
      String autor = txtAutor.getText();
      String editorial = txtEditorial.getText();
      Integer anio = Integer.parseInt(txtYear.getText());
      Integer pag = Integer.parseInt(txtPag.getText());
      String categoria = cmbCategory.getValue().toString();
      String formato = cmbFormat.getValue().toString();

      if(servicio.buscarTitulo(titulo).isPresent()){
        mostrarAlerta(Alert.AlertType.WARNING, "Datos existentes", "Este libro ya existe");
        return;
      }

      Libro libro = new Libro(titulo, autor, editorial, formato, anio, pag,  categoria);

      servicio.insertar(libro);

      listarTodos();
      limpiar();

      mostrarAlerta(Alert.AlertType.INFORMATION,
        "Éxito",
        "Libro agregado correctamente.");

    } catch (NumberFormatException e) {
      mostrarAlerta(Alert.AlertType.ERROR,
        "Error",
        "El año y el número de páginas deben ser números.");
    } catch (NullPointerException e) {
      mostrarAlerta(Alert.AlertType.WARNING,
        "Advertencia",
        "Debe seleccionar una categoría y un formato.");
    }
  }

  @FXML
  public void actualizarLibro(){
    if (libroSeleccionado == null) {
      mostrarAlerta(Alert.AlertType.WARNING, "Datos Incompletos", "Debe seleccionar un libro para actualizar");
      return;
    }
    libroSeleccionado.setTitulo(txtTitle.getText());
    libroSeleccionado.setAutor(txtAutor.getText());
    libroSeleccionado.setEditorial(txtEditorial.getText());
    libroSeleccionado.setAnio_publicacion(Integer.parseInt(txtYear.getText()));
    libroSeleccionado.setNumero_paginas(Integer.parseInt(txtPag.getText()));
    libroSeleccionado.setCategoria(cmbCategory.getValue().toString());
    libroSeleccionado.setFormato(cmbFormat.getValue().toString());
    if(rbDisponible.isSelected()){
      libroSeleccionado.setUsuario(null);
    }

    if (confirmar("Actualizar", "¿Está seguro de actualizar este libro?")) {
      servicio.actualizar(libroSeleccionado);
      mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Libro actualizado correctamente.");
      tblLibros.refresh();
    }


  }

  @FXML
  public void eliminarLibro(){
    if (libroSeleccionado == null) {
      mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Seleccione un libro.");
      return;
    }
    Integer id=libroSeleccionado.getId();
    if(confirmar("Eliminación", "¿Está seguro de eliminar este libro? Los cambios se guardaran permanentemente")){
      servicio.eliminar(id);
      mostrarAlerta(Alert.AlertType.INFORMATION, "¡Hecho!", "Cambios realizados con éxito");
      libroSeleccionado= null;
      listarTodos();
    }

  }

  @FXML
  public void regresar(){
    try {
      FXMLLoader loader = new FXMLLoader(
              getClass().getResource("login.fxml")
      );
      loader.setControllerFactory(context::getBean);
      Parent root = loader.load();

      Stage stage = new Stage();
      stage.setTitle("Gestión de Libros");
      stage.setScene(new Scene(root));
      stage.show();

      Stage ventanaActual = (Stage) txtTitle.getScene().getWindow();
      ventanaActual.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
   public void reservar(){
     if(libroSeleccionado == null){
       mostrarAlerta(Alert.AlertType.WARNING, "Reserva", "Seleccione un libro");
       return;
     }
     if(libroSeleccionado.getUsuario()!=null){
       mostrarAlerta(Alert.AlertType.WARNING, "Reserva", "El libro ya está reservado");
       return;
     }
     libroSeleccionado.setUsuario(usuarioregistrado);
     servicio.actualizar(libroSeleccionado);
     tblLibros.setItems(FXCollections.observableArrayList(servicio.listarTodos()));
     tblLibros.refresh();
   }

}
