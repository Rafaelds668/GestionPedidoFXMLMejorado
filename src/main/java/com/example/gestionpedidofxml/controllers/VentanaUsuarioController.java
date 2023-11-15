package com.example.gestionpedidofxml.controllers;

import com.example.gestionpedidofxml.Main;
import com.example.gestionpedidofxml.Session;
import com.example.gestionpedidofxml.domain.orders.Pedido;
import com.example.gestionpedidofxml.domain.user.UsuarioDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaUsuarioController implements Initializable {

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
    private TableColumn<Pedido, String> colCodPedido;

    @javafx.fxml.FXML
    private TableColumn<Pedido, String> colFecha;

    @javafx.fxml.FXML
    private TableColumn<Pedido, String> colUsuario;

    @javafx.fxml.FXML
    private TableColumn<Pedido, String> colTotal;

    @javafx.fxml.FXML
    private Button btnNuevoPedido;

    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
        Session.setCurrentUser(null);
        try{
            Main.changeScene("ventana-login.fxml", "Login");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("entrando en ventana...");

        colIdPedido.setCellValueFactory( (fila)->{
            return new SimpleStringProperty(fila.getValue().getId()+"");
        } );
        colCodPedido.setCellValueFactory( (fila)->{
            return new SimpleStringProperty(fila.getValue().getCodigo());
        } );
        colIdPedido.setCellValueFactory( (fila)->{
            return new SimpleStringProperty(fila.getValue().getId()+"");
        } );
        colFecha.setCellValueFactory( (fila)->{
            return new SimpleStringProperty(fila.getValue().getFecha());
        } );
        colUsuario.setCellValueFactory( (fila)->{
            return new SimpleStringProperty(fila.getValue().getUsuario()+"");
        } );
        colTotal.setCellValueFactory( (fila)->{
            return new SimpleStringProperty(fila.getValue().getTotal()+"");
        } );

        tvPedidos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pedido>() {
            @Override
            public void changed(ObservableValue<? extends Pedido> observableValue, Pedido pedido, Pedido t1) {
                Session.setCurrentOrder(t1);
                try {
                    Main.changeScene("ventana-pedido.fxml", "Producto del Pedido");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //Actualizo el usuario desde la base de datos

        Session.setCurrentUser((new UsuarioDAO()).get(Session.getCurrentUser().getId()) );
        System.out.println(Session.getCurrentUser());

        tvPedidos.getItems().addAll(Session.getCurrentUser().getPedidos());

        lTotal.setText(Session.getCurrentUser().getPedidosQuantity() + " pedidos");
        info.setText("Bienvenido "+ Session.getCurrentUser().getNombre());

    }
    @javafx.fxml.FXML
    public void nuevoPedido (ActionEvent actionEvent){

    }
}

