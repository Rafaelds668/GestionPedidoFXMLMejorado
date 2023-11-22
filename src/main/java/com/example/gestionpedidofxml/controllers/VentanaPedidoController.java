package com.example.gestionpedidofxml.controllers;

import com.example.gestionpedidofxml.Main;
import com.example.gestionpedidofxml.domain.Session;
import com.example.gestionpedidofxml.domain.item.Item;
import com.example.gestionpedidofxml.domain.item.ItemDAO;
import com.example.gestionpedidofxml.domain.orders.PedidoDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VentanaPedidoController  implements Initializable {

    @javafx.fxml.FXML
    private MenuItem menubarLogout;

    @javafx.fxml.FXML
    private MenuItem menuVolver;

    @javafx.fxml.FXML
    private TableView <Item> tvPedido;

    @javafx.fxml.FXML
    private TableColumn<Item, String> colNombre;

    @javafx.fxml.FXML
    private TableColumn<Item, String> colCantidad;

    @javafx.fxml.FXML
    private TableColumn<Item, String> colPrecio;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PedidoDAO dao = new PedidoDAO();
        List<Item> items = dao.detallesPedido(Session.getPedidoPulsado());

        //Rellenar la tabla
        colNombre. setCellValueFactory( (fila) -> {
            String nombre = fila.getValue().getProducto().getNombre();
            return new SimpleStringProperty(nombre);
        });
        colCantidad. setCellValueFactory( (fila) -> {
            int cantidad = fila.getValue().getCantidad();
            return new SimpleStringProperty(Integer.toString(cantidad));
        });
        colPrecio. setCellValueFactory( (fila) -> {
            double precio = fila.getValue().getProducto().getPrecio();
            return new SimpleStringProperty(Double.toString(precio));
        });
        ObservableList<Item> observableList = FXCollections.observableArrayList();
        observableList.addAll(items);
        tvPedido.setItems(observableList);


    }

    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
        Session.setCurrentUser(null);
        Main.loadFXML("ventana-login.fxml", "Login");
    }

    @javafx.fxml.FXML
    public void volver (ActionEvent actionEvent){ Main.loadFXML("pedidosview.fxml", "Lista Pedido");}

}
