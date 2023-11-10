module com.example.gestionpedidofxml {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gestionpedidofxml to javafx.fxml;
    exports com.example.gestionpedidofxml;
}