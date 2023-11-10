module com.example.gestionpedidofxml {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gestionpedidofxml to javafx.fxml;
    exports com.example.gestionpedidofxml;
    exports com.example.gestionpedidofxml.controllers;
    opens com.example.gestionpedidofxml.controllers to javafx.fxml;
}