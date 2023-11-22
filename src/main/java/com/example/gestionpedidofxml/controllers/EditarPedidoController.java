package com.example.gestionpedidofxml.controllers;

import com.example.gestionpedidofxml.Main;
import com.example.gestionpedidofxml.domain.Session;
import com.example.gestionpedidofxml.domain.item.Item;
import com.example.gestionpedidofxml.domain.item.ItemDAO;
import com.example.gestionpedidofxml.domain.orders.Pedido;
import com.example.gestionpedidofxml.domain.orders.PedidoDAO;
import com.example.gestionpedidofxml.domain.products.Producto;
import com.example.gestionpedidofxml.domain.products.ProductoDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class EditarPedidoController implements Initializable {

    private static PedidoDAO pedidoDAO;
    private static ItemDAO itemDAO;
    private static ProductoDAO productoDAO;

    @FXML
    private TableView<Item> tvDetallePedido;

    @FXML
    private Label labelTitulo;

    @FXML
    private TableColumn<Item, String> colNombre;
    @FXML
    private TableColumn<Item, String> colPrecio;
    @FXML
    private TableColumn<Item, String> colCantidad;

    @javafx.fxml.FXML
    private MenuItem menuLogout;

    @javafx.fxml.FXML
    private MenuItem menuAtras;

    @FXML
    private VBox vBoxMenu;
    @FXML
    private Spinner<Integer> spinnerCantidad;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnAniadir;
    @FXML
    private ComboBox<String> comboNombre;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnCancelar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pedidoDAO = new PedidoDAO();
        itemDAO = new ItemDAO();
        productoDAO = new ProductoDAO();

        //Si es un pedido Nuevo
        if(Session.isEsNuevoPedido()){
            Pedido nuevoPedido = new Pedido();
            nuevoPedido.setUsuario(Session.getCurrentUser());
            nuevoPedido.setFecha(LocalDate.now().toString());
            pedidoDAO.save(nuevoPedido);
            Session.setPedidoPulsado(nuevoPedido);
            //Cambiar Titulo
            labelTitulo.setText("Añadir Pedido");
        }

        //Si no es un nuevo Pedido
        else {
            //Rellenar la tabla
            rellenarTabla();
            //cambiar titulo
            if(Session.getCurrentOrder()!=null) labelTitulo.setText("Editar Pedido");
        }
        //Añadir listener
        añadirListenerTabla(    );

        //Rellenar comoBox
        comboNombre.getItems().addAll(pedidoDAO.todosLosProductos());
    }


    private void añadirListenerTabla(){
        tvDetallePedido.getSelectionModel( ).selectedItemProperty( ).addListener( ( observableValue , producto , t1 ) -> {

            vBoxMenu.setDisable( false );
            if (t1 != null) Session.setItemPulsado( t1 );
            comboNombre.setValue( Session.getItemPulsado( ).getProducto( ).getNombre( ) );
            spinnerCantidad.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory( 1 , 1000 , Session.getItemPulsado( ).getCantidad( ) , 1 ) );
            btnEditar.setText( "Editar" );
            comboNombre.setDisable( true );
        } );
    }

    private void rellenarTabla(){
        List<Item> items = pedidoDAO.detallesPedido( Session.getPedidoPulsado( ) );
        colNombre.setCellValueFactory( ( fila ) -> {
            String nombre = fila.getValue( ).getProducto( ).getNombre( );
            return new SimpleStringProperty( nombre );
        } );
        colCantidad.setCellValueFactory( ( fila ) -> {
            int cantidad = fila.getValue( ).getCantidad( );
            return new SimpleStringProperty( Integer.toString( cantidad ) );
        } );
        colPrecio.setCellValueFactory( ( fila ) -> {
            double precio = fila.getValue( ).getProducto( ).getPrecio( );
            return new SimpleStringProperty( Double.toString( precio ) );
        } );
        ObservableList<Item> observableList = FXCollections.observableArrayList( );
        observableList.addAll( items );
        tvDetallePedido.setItems( observableList );
    }

    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
        Session.setCurrentUser(null);
        Main.loadFXML("ventana-login.fxml", "Login");
    }

    @javafx.fxml.FXML
    public void atras (ActionEvent actionEvent){ Main.loadFXML("pedidosview.fxml", "Lista Pedido");}

    @FXML
    public void editar(){
        try {
            if (Session.isEsUnNuevoProducto( )) {
                if (pedidoDAO.estaProductoEnPedido( comboNombre.getValue( ) , Session.getPedidoPulsado( ) )) {
                    Item item = itemDAO.itemPedidoNombre( Session.getPedidoPulsado( ) , comboNombre.getValue( ) );
                    modificaItem( spinnerCantidad.getValue( ) + item.getCantidad( ) );
                } else {
                    Producto producto = productoDAO.productoPorNombre( comboNombre.getValue( ) );
                    pedidoDAO.insertarItemPedido( Session.getPedidoPulsado( ) , spinnerCantidad.getValue( ) , producto );
                }
                Session.setEsUnNuevoProducto( false );
            } else {
                modificaItem( spinnerCantidad.getValue( ) );
            }
            pedidoDAO.actualizarFecha(Session.getPedidoPulsado());
            this.rellenarTabla( );
            this.vBoxMenu.setDisable( true );
            this.tvDetallePedido.setDisable( false );

        } catch ( NumberFormatException e ) {

            System.out.println( e.getMessage( ) );
        }
    }

    private void modificaItem( Integer cantidad ) {
        Item itemModificado = itemDAO.itemPedidoNombre( Session.getPedidoPulsado( ) ,
                comboNombre.getValue( ) );
        Producto productoModificado = productoDAO.productoPorNombre( comboNombre.getValue( ) );
        itemModificado.setCantidad( cantidad );
        itemModificado.setProducto( productoModificado );
        itemDAO.update( itemModificado );
    }

    @FXML
    public void añadir( ) {
        Session.setEsUnNuevoProducto( true );
        comboNombre.setDisable( false );
        vBoxMenu.setDisable( false );
        tvDetallePedido.setDisable( true );
        btnEditar.setText( "Añadir" );
        comboNombre.setValue( comboNombre.getItems( ).getFirst( ) );
        spinnerCantidad.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory( 1 , 1000 , 1 , 1 ) );
    }

    @FXML
    public void eliminar( ) {
        if (Session.getItemPulsado( ) != null) {
            itemDAO.delete( Session.getItemPulsado( ) );
            this.rellenarTabla( );
            this.vBoxMenu.setDisable( true );
        }
    }
}

