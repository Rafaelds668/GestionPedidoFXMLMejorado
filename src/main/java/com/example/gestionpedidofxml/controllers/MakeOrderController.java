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

/**
 * Controlador de la interfaz para realizar un nuevo pedido.
 * Implementa la interfaz {@code Initializable} de JavaFX.
 */
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

    /**
     * Método de inicialización que activa el comboBox y el spinner
     *
     * @param url             La ubicación relativa del archivo FXML.
     * @param resourceBundle  Los recursos específicos del local.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableListProducto = FXCollections.observableArrayList();
        ProductoDAO productoDAO = new ProductoDAO();
        observableListProducto.setAll(productoDAO.getAll());
        comboProducto.setItems(observableListProducto);
        spCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1, 1));
    }
    /**
     * Método para cerrar sesión, estableciendo el usuario actual en null y cargando la pantalla de inicio de sesión.
     */
    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent){
        Session.setCurrentOrder(null);
        Main.loadFXML("login.fxml", "Login");
    }
    /**
     * Método para mostrar información acerca del creador de la aplicación en un cuadro de diálogo.
     */
    @javafx.fxml.FXML
    public void mostarAcercaDe(ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de ");
        alert.setHeaderText("Creado por ");
        alert.setContentText("Rafael Delgado Shepherd desde 2ºDAM");
        alert.showAndWait();
    }

    /**
     * Maneja el evento de aceptar al realizar una acción, como agregar un ítem al pedido.
     *
     * @param actionEvent El evento de acción que desencadena esta operación.
     */
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

    /**
     * Método para regresar a la vista principal de pedidos del usuario actual.
     */
    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent){
        Main.loadFXML("user-view.fxml", "Pediddos de " + Session.getCurrentUser().getNombre());
    }

}
