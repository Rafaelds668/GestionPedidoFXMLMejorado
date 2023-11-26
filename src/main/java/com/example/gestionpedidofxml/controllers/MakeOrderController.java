package com.example.gestionpedidofxml.controllers;

import com.example.gestionpedidofxml.Main;
import com.example.gestionpedidofxml.domain.Session;
import com.example.gestionpedidofxml.domain.item.Item;
import com.example.gestionpedidofxml.domain.item.ItemDAO;
import com.example.gestionpedidofxml.domain.orders.Pedido;
import com.example.gestionpedidofxml.domain.products.Producto;
import com.example.gestionpedidofxml.domain.products.ProductoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MakeOrderController implements Initializable {
    @javafx.fxml.FXML
    private Spinner<Integer> spCantidad;
    @javafx.fxml.FXML
    private ComboBox<Producto> comboProducto;
    @javafx.fxml.FXML
    private Button btnAceptar;
    @javafx.fxml.FXML
    private Button btnVolver;

    private ObservableList<Producto> observableListProducto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableListProducto = FXCollections.observableArrayList();
        ProductoDAO productoDAO = new ProductoDAO();
        observableListProducto.setAll(productoDAO.getAll());
        comboProducto.setItems(observableListProducto);
        spCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1, 1));
    }
    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent){
        Session.setCurrentOrder(null);
        Main.loadFXML("login.fxml", "Login");
    }
    @javafx.fxml.FXML
    public void mostarAcercaDe(ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de ");
        alert.setHeaderText("Creado por ");
        alert.setContentText("Rafael Delgado Shepherd desde 2ºDAM");
        alert.showAndWait();
    }

    @javafx.fxml.FXML
    public void aceptar(ActionEvent actionEvent){
        Pedido pedido = Session.getCurrentOrder();
        if(pedido != null){
            Item item = new Item();
            item.setPedido(pedido);
            item.setCantidad(spCantidad.getValue());
            item.setProducto(comboProducto.getSelectionModel().getSelectedItem());

            Session.setItem(new ItemDAO().save(item));
            Session.setItem(item);
            Main.loadFXML("details-view.fxml", "Pedido numero" + Session.getCurrentOrder().getCodigo());
        }
    }

    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent){
        Main.loadFXML("user-view.fxml", "Pediddos de " + Session.getCurrentUser().getNombre());
    }

}
