package com.example.gestionpedidofxml.controllers;

import com.example.gestionpedidofxml.Main;
import com.example.gestionpedidofxml.domain.Session;
import com.example.gestionpedidofxml.domain.item.Item;
import com.example.gestionpedidofxml.domain.item.ItemDAO;
import com.example.gestionpedidofxml.domain.orders.Pedido;
import com.example.gestionpedidofxml.domain.orders.PedidoDAO;
import com.example.gestionpedidofxml.domain.products.Producto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailsViewController implements Initializable {

    @javafx.fxml.FXML
    private TableColumn<Item, String> cIdItem;
    @javafx.fxml.FXML
    private TableColumn<Item, String> cCodigoPedido;
    @javafx.fxml.FXML
    private TableColumn<Item, String> cProducto;
    @javafx.fxml.FXML
    private TableColumn<Item, String> cCantidad;
    @javafx.fxml.FXML
    private TableView<Item> tItem;


    private ObservableList<Item> observableListItem;

    private ItemDAO itemDAO = new ItemDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Configuracion de las columnas de la tabla y carga de datos
        cIdItem.setCellValueFactory((fila) -> {
            String id_item = String.valueOf(fila.getValue().getId());
            return new SimpleStringProperty(id_item);
        });

        cCodigoPedido.setCellValueFactory((fila) -> {
            String codigo_pedido = String.valueOf(fila.getValue().getPedido().getCodigo());
            return new SimpleStringProperty(codigo_pedido);
        });

        cCantidad.setCellValueFactory((fila) -> {
            String cantidad = String.valueOf(fila.getValue().getCantidad());
            return new SimpleStringProperty(cantidad);
        });

        cProducto.setCellValueFactory((fila) -> {
            String producto = String.valueOf(fila.getValue().getProducto().getNombre() + "/"
                    + fila.getValue().getProducto().getPrecio() + "€");
            return new SimpleStringProperty(producto);
        });

        /*Creo una lista observable para contener los elementos (detalles de pedido)
            que se mostraran en la tabla
         */
        observableListItem = FXCollections.observableArrayList();

        Session.setCurrentOrder((new PedidoDAO()).get(Session.getCurrentOrder().getId()));
        System.out.println(Session.getCurrentOrder());
        //Agrego los detalles de los pedidos (items) cargados previamente a la lista observable
        observableListItem.setAll(Session.getCurrentOrder().getItems());
        //Establezco la lista observable como el conjunto de elementos que se mostraran el la tabla
        tItem.setItems(observableListItem);
    }

    public void logout(){
        Session.setCurrentUser(null);
        Main.loadFXML("login.fxml", "Login");
    }
    public void volver(){
        Main.loadFXML("user-view.fxml", "Pedidos de " + Session.getCurrentUser().getNombre());
    }
    public void mostarAcercaDe(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de ");
        alert.setHeaderText("Creado por ");
        alert.setContentText("Rafael Delgado Shepherd desde 2ºDAM");
        alert.showAndWait();
    }

    @javafx.fxml.FXML
    public void addItem(){
        var item = new Item();
        Session.setItem(item);
        Main.loadFXML("hacer-pedido.fxml", "Añadir item en el pedido " + Session.getCurrentOrder().getCodigo());
    }

    @javafx.fxml.FXML
    public void deleteItem(){

        //Se coge el item seleccionado.
        Item itemSeleccionado = tItem.getSelectionModel().getSelectedItem();

        //Confirmación de eliminación mediante un diálogo de confirmación.
        if (itemSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("¿Deseas borrar el item: " + itemSeleccionado.getId() + ", que contiene el producto: " + itemSeleccionado.getProducto().getNombre() + "?");
            var result = alert.showAndWait().get();

            //Si se confirma la eliminación, se borra el ítem seleccionado de la lista y de la base de datos.
            if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                itemDAO.delete(itemSeleccionado);
                observableListItem.remove(itemSeleccionado);
            }
        } else {
            //Muestra un mensaje de error o advertencia al usuario si no se ha seleccionado ningún pedido para eliminar.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Por favor, selecciona un pedido para eliminar.");
            alert.showAndWait();
        }
    }
}

