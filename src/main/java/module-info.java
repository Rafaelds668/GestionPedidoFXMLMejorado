module com.example.gestionpedidofxml {
    requires javafx.controls;
    requires javafx.fxml;

    requires lombok;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;

    opens com.example.gestionpedidofxml.domain.user;
    opens com.example.gestionpedidofxml.domain.item;
    opens com.example.gestionpedidofxml.domain.orders;
    opens com.example.gestionpedidofxml.domain.products;


    opens com.example.gestionpedidofxml to javafx.fxml;
    opens com.example.gestionpedidofxml.controllers to javafx.fxml;

    exports com.example.gestionpedidofxml;
    exports com.example.gestionpedidofxml.controllers;
}