package com.example.gestionpedidofxml.controllers;

import com.example.gestionpedidofxml.Main;
import com.example.gestionpedidofxml.domain.Session;
import com.example.gestionpedidofxml.domain.HibernateUtil;
import com.example.gestionpedidofxml.domain.user.Usuario;
import com.example.gestionpedidofxml.domain.user.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador de la interfaz de inicio de sesión.
 * Implementa la interfaz {@code Initializable} de JavaFX.
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnInicar;
    @FXML
    private Label info;

    /**
     * Método de inicialización que se llama automáticamente al cargar la interfaz.
     *
     * @param url             La ubicación relativa del archivo FXML.
     * @param resourceBundle  Los recursos específicos del local.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Método de acción que se activa al presionar el botón de inicio de sesión.
     * Valida los datos de usuario y contraseña ingresados y realiza el acceso correspondiente.
     *
     * @param actionEvent  Evento de acción asociado al botón de inicio de sesión.
     */
    @FXML
    public void login(ActionEvent actionEvent){
        String user = txtUser.getText();
        String pass = txtPassword.getText();

        if( user.length()<4 || pass.length()<4 ){
            info.setText("Introduce los datos");
            info.setStyle("-fx-text-fill: red;");

        } else{

            // ACCESO A BASE DE DATOS PARA LA VALIDACION
            Usuario u = (new UsuarioDAO()).validateUser( user, pass );

            if(u==null){
                info.setText("Error, datos incorrectos");
                info.setStyle("-fx-text-fill: red;");
            }else {
                Session.setCurrentUser(u);

                // Guardar usuario en sesión e ir a la proxima ventana
                Main.loadFXML("user-view.fxml", "Pedido de " + Session.getCurrentUser().getNombre());
            }

        }
    }
}