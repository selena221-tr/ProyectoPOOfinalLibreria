open module epn.esfot.proyectofinallibreria {

  requires javafx.controls;
  requires javafx.fxml;

  requires org.kordamp.bootstrapfx.core;
  requires jakarta.persistence;
  requires spring.context;
  requires spring.data.jpa;
  requires spring.boot.autoconfigure;
  requires spring.beans;
  requires spring.boot;

  exports epn.esfot.proyectofinallibreria;
  requires org.hibernate.orm.core;
    requires org.apache.tomcat.embed.core;
    requires jbcrypt;
}
