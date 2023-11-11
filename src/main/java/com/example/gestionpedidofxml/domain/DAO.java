package com.example.gestionpedidofxml.domain;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DAO <T> {
    ArrayList<T> loadAll (String codigo_Pedido) throws SQLException;


}
