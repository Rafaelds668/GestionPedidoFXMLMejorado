package com.example.gestionpedidofxml.controllers;

import com.example.gestionpedidofxml.Main;
import com.example.gestionpedidofxml.domain.user.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.nio.file.attribute.UserPrincipal;

public class CrearSesionController {

    @FXML
    private MenuItem menuVolver;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField pssContraseña;

    @FXML
    private PasswordField pssRepetirContraseña;

    @FXML
    private Label info;

    @FXML
    private Button btnCrearSesion;

    private Usuario userService = new Usuario();

    @javafx.fxml.FXML
    public void volver (ActionEvent actionEvent){
        Main.loadFXML("ventana-login.fxml", "Login");
    }
    @javafx.fxml.FXML
    public void crearUsuario(ActionEvent actionEvent){


    }
}
