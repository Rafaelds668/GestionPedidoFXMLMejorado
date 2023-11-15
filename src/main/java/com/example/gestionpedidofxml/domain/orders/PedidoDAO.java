package com.example.gestionpedidofxml.domain.orders;

import com.example.gestionpedidofxml.domain.DAO;
import com.example.gestionpedidofxml.domain.HibernateUtil;
import lombok.extern.java.Log;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;

@Log
public class PedidoDAO implements DAO<Pedido> {

    public static final HashMap<String,String> QUERY_ATTR;

    static {
        QUERY_ATTR = new HashMap<>();
        QUERY_ATTR.put("id","select distinct(pedido.id) from Pedido pedido");
        QUERY_ATTR.put("codigo","select distinct(pedido.codigo) from Pedido pedido");
        QUERY_ATTR.put("fecha","select distinct(pedido.fecha) from Pedido pedido");
        QUERY_ATTR.put("usuario","select distinct(usuario.studio) from Pedido pedido");
        QUERY_ATTR.put("total","select distinct(total.studio) from Pedido pedido");
    }

    @Override
    public ArrayList<Pedido> getAll() {
        return null;
    }

    @Override
    public Pedido get(Long id) {
        return null;
    }

    @Override
    public Pedido save(Pedido data) {
        return null;
    }

    @Override
    public void update(Pedido data) {
    }

    @Override
    public void delete(Pedido data) {

    }
}
