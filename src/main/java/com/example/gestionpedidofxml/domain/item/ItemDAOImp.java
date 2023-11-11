package com.example.gestionpedidofxml.domain.item;

import com.example.gestionpedidofxml.domain.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementación de la interfaz ItemDAO para acceder y gestionar datos de ítems en una base de datos.
 */
public class ItemDAOImp implements DAO<Item> {
    /**
     * Conexión a la base de datos utilizada para acceder a los datos de ítems.
     */
    private static Connection connection;

    /**
     * Consulta SQL para cargar todos los ítems de un pedido por su código.
     */
    private static final String queryLoadAll = "SELECT * FROM item WHERE codigo_pedido = ?";

    /**
     * Constructor de la clase ItemDAOImp.
     *
     * @param conn Conexión a la base de datos que se utilizará para acceder a los datos de ítems.
     */
    public ItemDAOImp(Connection conn) {
        this.connection = conn;
    }

    @Override
    public ArrayList<Item> loadAll(String codigo_Pedido) throws SQLException {
        return null;
    }
}
