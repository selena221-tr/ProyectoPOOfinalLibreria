package epn.esfot.proyectofinallibreria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class HelloApplication extends Application {
  private ConfigurableApplicationContext context;
  @Override
  public void init() {
    context = new SpringApplicationBuilder(SpringBootConfig.class).run();
  }

  @Override
    public void start(Stage stage) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
    loader.setControllerFactory(context::getBean);
    Scene scene = new Scene(loader.load(), 600, 470);
    stage.setTitle("Inicio!");
    stage.setScene(scene);
    stage.show();
  }
  @Override
  public void stop() {
    context.close();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
