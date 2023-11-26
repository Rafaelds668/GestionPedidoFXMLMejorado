package com.example.gestionpedidofxml.controllers;

import com.example.gestionpedidofxml.Main;
import com.example.gestionpedidofxml.domain.HibernateUtil;
import com.example.gestionpedidofxml.domain.Session;
import com.example.gestionpedidofxml.domain.item.Item;
import com.example.gestionpedidofxml.domain.orders.Pedido;
import com.example.gestionpedidofxml.domain.orders.PedidoDAO;
import com.example.gestionpedidofxml.domain.user.UsuarioDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import org.hibernate.query.Query;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {

    @javafx.fxml.FXML
    private MenuItem menuLogout;

    @javafx.fxml.FXML
    private Label lblUsuario; //Etiqueta para mostrar el nombre del usuario

    @javafx.fxml.FXML
    private TableView<Pedido> tPedidos;  //Tabla para mostrar los pedidos del usuario


    @javafx.fxml.FXML
    private TableColumn<Pedido, String> cIdPedido; // Columna para mostrar el ID de los pedidos.

    @javafx.fxml.FXML
    private TableColumn<Pedido, String> cCodigoPedido;  // Columna para mostrar el código de los pedidos.

    @javafx.fxml.FXML
    private TableColumn<Pedido, String> cFecha;  // Columna para mostrar la fecha de los pedidos.

    @javafx.fxml.FXML
    private TableColumn<Pedido, String> cUsuario;  // Columna para mostrar el usuario relacionado con los pedidos.

    @javafx.fxml.FXML
    private TableColumn<Pedido, String> cTotal;   // Columna para mostrar el total de los pedidos.

    @javafx.fxml.FXML
    private Button btnDelete;

    @javafx.fxml.FXML
    private Button btnAdd;

    private ObservableList<Pedido> observablePedido; //Lista observable para almacenar y mostrar los pedidod

    private final PedidoDAO pedidoDAO = new PedidoDAO(); //Creo una instancia de PedidoDAO

    public UserViewController(){}
    /**
     * Inicializa la vista del controlador, configurando la tabla de pedidos y otros elementos visuales.
     *
     * @param url            Ubicación relativa del archivo FXML.
     * @param resourceBundle Recursos específicos del idioma.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
//Rellenar la tabla
        cIdPedido.setCellValueFactory( ( fila ) -> {
            Long id = fila.getValue( ).getId( );
            return new SimpleStringProperty( id.toString( ) );
        } );
        cCodigoPedido.setCellValueFactory((fila) ->{
            String codigo = fila.getValue().getCodigo();
            return new SimpleStringProperty(codigo);
        });
        cFecha.setCellValueFactory( ( fila ) -> {
            String fecha = fila.getValue( ).getFecha( );
            return new SimpleStringProperty( fecha );
        } );
        cUsuario.setCellValueFactory( ( fila ) -> {
            String nombre = Session.getCurrentUser().getNombre();
            return new SimpleStringProperty( nombre );
        } );
        cTotal.setCellValueFactory( ( fila ) -> {
            String total = String.valueOf(fila.getValue().getTotal() + " €");
            return new SimpleStringProperty(total);
        } );

        //Creo una lista observable para los pedidos y carga los pedidos del usuario actual
        observablePedido = FXCollections.observableArrayList();
        Session.setCurrentUser((new UsuarioDAO()).get(Session.getCurrentUser().getId()));
        cargarLista();

        //Muestra el nombre del usuario en una etiqueta de binvenida
        lblUsuario.setText("Bienvenido " + Session.getCurrentUser().getNombre());
        //Añade un listener a la tabla de pedidos para manejar la seleccion de un pedido
        tPedidos.getSelectionModel().selectedItemProperty().addListener((observableValue, pedido, t1) -> {
            Session.setCurrentOrder(t1);
        });

        //Si le doy 2 veces al pedido realiza la accion del
        tPedidos.setOnMouseClicked(event ->{
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                Pedido selectedPedido = tPedidos.getSelectionModel().getSelectedItem();
                if(selectedPedido != null){
                    Session.setCurrentOrder(selectedPedido);
                    Main.loadFXML("details-view.fxml", "Detalle Pedido " + Session.getCurrentOrder().getCodigo());
                }
            }
        });

    }
    /**
     * Carga la lista de pedidos del usuario actual en la tabla, calculando y estableciendo los totales de cada pedido.
     * Finalmente, asigna la lista Observable como los datos a mostrar en la tabla de pedidos.
     */
    private void cargarLista(){

//Obtiene la lista de pedidos del usuario actual y la asigna a la lista Observable.
        observablePedido.setAll(Session.getCurrentUser().getPedidos());

        //Recorre cada pedido en la lista Observable.
        for (Pedido order : observablePedido) {
            //Calcula el total del pedido y lo establece en el pedido actual.
            Double totalPedido = calcularTotalPedido(order);
            order.setTotal(totalPedido);
        }

        //Establece la lista Observable como los datos a mostrar en la tabla.
        tPedidos.setItems(observablePedido);
    }

    /**
     * Calcula el total del pedido sumando los productos de cada ítem multiplicados por su cantidad.
     *
     * @param order Pedido del cual se calcula el total.
     * @return El total calculado del pedido.
     */
    private Double calcularTotalPedido(Pedido order) {
        //Inicializa la variable total como 0.0 para almacenar el total del pedido.
        Double total  = 0.0;

        //Itera a través de los items del pedido para calcular el total.
        for (Item item : order.getItems()){

            //Obtiene el precio del producto y lo multiplica por la cantidad, sumando al total.
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        //Retorna el total calculado del pedido.
        return total;
    }
    /**
     * Método para cerrar sesión, estableciendo el usuario actual en null y cargando la pantalla de inicio de sesión.
     */
    public void logout(ActionEvent actionEvent){
        Session.setCurrentUser(null);
        Main.loadFXML("login.fxml", "Login");
    }

    /**
     * Método para mostrar información acerca del creador de la aplicación en un cuadro de diálogo.
     */
    public void mostrarAcercaDe (ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de");
        alert.setHeaderText("Creado por");
        alert.setContentText("Rafael Delgado Shepherd desde 2ºDAM");
        alert.showAndWait();
    }
    /**
     * Maneja el evento de agregar un nuevo pedido. Crea un nuevo pedido, asigna un código único, establece la fecha actual,
     * relaciona el usuario actual, guarda el pedido en la base de datos, actualiza la lista observable y muestra una
     * ventana de información con el código del nuevo pedido.
     *
     * @param actionEvent Evento que desencadena esta operación.
     */
    @javafx.fxml.FXML
    public void addPedido (ActionEvent actionEvent){
        Pedido pedidoAniadido = new Pedido();

        try (org.hibernate.Session s = HibernateUtil.getSessionFactory().openSession()){
            //Obtengo el ultimo codigo de los pedidos
            Query<String> q = s.createQuery("select max (p.codigo) from Pedido p", String.class);
            String ultimoCodigoPedido = q.uniqueResult();

            //Aumento en 1 el ultimo codigo de los pedidos
            int ultimoNumero = Integer.parseInt(ultimoCodigoPedido.substring(4));
            int nuevoNumero = ultimoNumero+1;
            String nuevoCodigoPedido = "PED-" + String.format("%03d", nuevoNumero);

            //Establecer el nuevo codigo en el pedido
            pedidoAniadido.setCodigo(nuevoCodigoPedido);
        }catch (Exception e){
            e.printStackTrace();
        }

        //Establecer la fecha actual por defecto
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaActual = dateFormat.format(new Date());
        pedidoAniadido.setFecha(fechaActual);

        pedidoAniadido.setUsuario(Session.getCurrentUser());
        pedidoAniadido.setId(0L);

        if (pedidoAniadido.getItems().isEmpty()){
            pedidoAniadido.setTotal(0.0);
        }

        //Agregar un nuevo pedido a la lista observable
        observablePedido.add(pedidoAniadido);

        //Actualizar la tabla
        tPedidos.setItems(observablePedido);
        Session.setCurrentOrder((new PedidoDAO()).save(pedidoAniadido));
        Session.setCurrentOrder(pedidoAniadido);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Enhorabuena");
        alert.setHeaderText("El pedido se ha añadido correctamente");
        alert.setContentText("El código del pedido es: " + Session.getCurrentOrder().getCodigo());
        alert.showAndWait();

    }

    /**
     * Maneja el evento de eliminar un pedido. Obtiene el pedido seleccionado, muestra un diálogo de confirmación y, si el usuario
     * confirma la eliminación, borra el pedido de la base de datos y lo quita de la lista observable de pedidos.
     *
     * @param actionEvent Evento que desencadena esta operación.
     */
    @javafx.fxml.FXML
    public void deletePedido (ActionEvent actionEvent){
        //Se coge el pedido seleccionado.
        Pedido pedidoSeleccionado = tPedidos.getSelectionModel().getSelectedItem();

        //Confirmación de eliminación mediante un diálogo de confirmación.
        if (pedidoSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("¿Deseas borrar el pedido: " + pedidoSeleccionado.getCodigo() + "?");
            var result = alert.showAndWait().get();

            //Si se confirma la eliminación, se borra el ítem seleccionado de la lista y de la base de datos.
            if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                pedidoDAO.delete(pedidoSeleccionado);
                observablePedido.remove(pedidoSeleccionado);
            }
        } else {
            //Muestra un mensaje de error o advertencia al usuario si no se ha seleccionado ningún pedido para eliminar.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Por favor, selecciona un pedido para eliminar.");
            alert.showAndWait();
        }

    }
}

