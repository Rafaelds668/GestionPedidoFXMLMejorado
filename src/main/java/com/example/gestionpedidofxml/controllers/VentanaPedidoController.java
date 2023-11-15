package com.example.gestionpedidofxml.controllers;

import com.example.gestionpedidofxml.Main;
import com.example.gestionpedidofxml.Session;
import com.example.gestionpedidofxml.domain.item.Item;
import com.example.gestionpedidofxml.domain.orders.Pedido;
import jakarta.persistence.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class VentanaPedidoController {

    @javafx.fxml.FXML
    private MenuItem menubarLogout;

    @javafx.fxml.FXML
    private MenuItem menuVolver;

    @javafx.fxml.FXML
    private TableView <Item> tvPedido;

    @javafx.fxml.FXML
    private TableColumn<Item, String> colId;

    @javafx.fxml.FXML
    private TableColumn<Item, String> colCodPedido;

    @javafx.fxml.FXML
    private TableColumn<Item, String> colCantidad;

    @javafx.fxml.FXML
    private TableColumn<Item, String> colProducto;

    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
        Session.setCurrentUser(null);
        Main.changeScene("ventana-login.fxml", "Login");
    }

    @javafx.fxml.FXML
    public void volver (ActionEvent actionEvent){ Main.changeScene("ventana-usuario.fxml", "Lista Pedido");}
}
