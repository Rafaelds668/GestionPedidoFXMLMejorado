package com.example.gestionpedidofxml.controllers;

import com.example.gestionpedidofxml.Main;
import com.example.gestionpedidofxml.domain.Session;
import com.example.gestionpedidofxml.domain.orders.Pedido;
import com.example.gestionpedidofxml.domain.orders.PedidoDAO;
import com.example.gestionpedidofxml.domain.user.Usuario;
import com.example.gestionpedidofxml.domain.user.UsuarioDAO;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PedidosController implements Initializable {

    PedidoDAO pedidoDAO;

    @javafx.fxml.FXML
    private MenuItem menuLogout;

    @javafx.fxml.FXML
    private Label labelTitulo;

    @javafx.fxml.FXML
    private Label info;

    @javafx.fxml.FXML
    private Label lTotal;

    @javafx.fxml.FXML
    private TableView<Pedido> tvPedidos;

    @javafx.fxml.FXML
    private TableColumn<Pedido, String> colIdPedido;

    @javafx.fxml.FXML
    private TableColumn<Pedido, String> colFecha;

    @javafx.fxml.FXML
    private TableColumn<Pedido, String> colUsuario;

    @javafx.fxml.FXML
    private TableColumn<Pedido, String> colTotal;

    @javafx.fxml.FXML
    private Button btnNuevoPedido;

    @javafx.fxml.FXML
    private Button btnEliminar;

    @javafx.fxml.FXML
    private Button btnEditar;

    @javafx.fxml.FXML
    private Button btnDetalles;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pedidoDAO = new PedidoDAO();

        Session.setEsUnNuevoProducto(false);
        rellenarTabla();

        tvPedidos.getSelectionModel().selectedItemProperty().addListener((observableValue, pedido, t1) -> {
            Session.setPedidoPulsado(t1);
        });

    }

    private void rellenarTabla(){

        Usuario usuario = Session.getCurrentUser( );
        pedidoDAO = new PedidoDAO( );
        List<Pedido> pedidosDeUser = pedidoDAO.pedidosUsuario( usuario );

        //Cambiar Titulo
        labelTitulo.setText( "Pedidos de " + usuario.getNombre( ) );
        //Rellenar la tabla
        colIdPedido.setCellValueFactory( ( fila ) -> {
            Long id = fila.getValue( ).getId( );
            return new SimpleStringProperty( id.toString( ) );
        } );
        colFecha.setCellValueFactory( ( fila ) -> {
            String fecha = fila.getValue( ).getFecha( );
            return new SimpleStringProperty( fecha );
        } );
        colUsuario.setCellValueFactory( ( fila ) -> {
            String nombre = Session.getCurrentUser( ).getNombre( );
            return new SimpleStringProperty( nombre );
        } );
        colTotal.setCellValueFactory( ( fila ) -> {
            String total = fila.getValue( ).getTotal( );
            return new SimpleStringProperty( total );
        } );
        ObservableList<Pedido> observableList = FXCollections.observableArrayList( );
        observableList.addAll( pedidosDeUser );
        tvPedidos.setItems( observableList );

    }
    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
        Session.setCurrentUser(null);
        try{
            Main.loadFXML("ventana-login.fxml", "Login");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void eliminar(ActionEvent actionEvent){
        if (Session.getPedidoPulsado() != null){
            pedidoDAO.delete(Session.getPedidoPulsado());
            this.rellenarTabla();
        }
    }

    @javafx.fxml.FXML
    public void editar(ActionEvent actionEvent){
        if(Session.getPedidoPulsado() != null){
            Main.loadFXML("editarPedido.fxml", "Editar Pedido");
        }
    }
    @javafx.fxml.FXML
    public void nuevoPedido (ActionEvent actionEvent){
        Session.setEsNuevoPedido(true);
        Main.loadFXML("editarPedido.fxml", "Añadir Pedido");
    }

    @javafx.fxml.FXML
    public void detalles(ActionEvent actionEvent){
        if(Session.getPedidoPulsado() != null)
            Main.loadFXML("ventana-pedido.fxml", "Detalles del pedido");

    }

}

