package com.example.gestionpedidofxml.domain.item;

import com.example.gestionpedidofxml.domain.DAO;
import jakarta.persistence.SecondaryTable;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemDAO implements DAO<Item> {

    public static final HashMap<String,String> QUERY_ATTR;

    static {
        QUERY_ATTR = new HashMap<>();
        QUERY_ATTR.put("id","select distinct(item.id) form Item item");
        QUERY_ATTR.put("codigo_pedido","select distinct(item.codigo_pedido) form Item item");
        QUERY_ATTR.put("producto_id","select distinct(item.producto_id) form Item item");
        QUERY_ATTR.put("cantidad","select distinct(item.cantidad) form Item item");
    }
    @Override
    public ArrayList<Item> getAll() {
        return null;
    }

    @Override
    public Item get(Long id) {
        return null;
    }

    @Override
    public Item save(Item data) {
        return null;
    }

    @Override
    public void update(Item data) {

    }

    @Override
    public void delete(Item data) {

    }
}
