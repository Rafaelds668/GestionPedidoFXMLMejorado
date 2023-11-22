package com.example.gestionpedidofxml.controllers;

import com.example.gestionpedidofxml.Main;
import com.example.gestionpedidofxml.domain.Session;
import com.example.gestionpedidofxml.domain.HibernateUtil;
import com.example.gestionpedidofxml.domain.user.Usuario;
import com.example.gestionpedidofxml.domain.user.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnInicar;
    @FXML
    private Label info;

    @FXML
    private Button btnCrearSesion;

    public LoginController(){}

    @FXML
    public void login(ActionEvent actionEvent){
      String email = txtUser.getText();
      String password = txtPassword.getText();

          /*ACCSO A BASE DE DATOS PARA LA VALIDACION*/
          Usuario usuario = (new UsuarioDAO()).validateUser(email, password);

          if(usuario == null){
              info.setText("Usuario no encontrado");
          }else {
              info.setText("Usuario " + usuario.getNombre() + "("+ password + ") correcto");

              Session.setCurrentUser(usuario);
              System.out.println(usuario);
              /*Guardar usuario en sesion e ir a la proxima ventana*/

              try {
                  Main.loadFXML("pedidosview.fxml", "Pedidos");
              } catch (Exception e) {
                  throw new RuntimeException(e);
              }
          }
      }




    @FXML
    public void crearSesion(ActionEvent actionEvent){
        Main.loadFXML("ventana-crearSesion.fxml", "Crear Sesion");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HibernateUtil.getSessionFactory().openSession();
    }
}