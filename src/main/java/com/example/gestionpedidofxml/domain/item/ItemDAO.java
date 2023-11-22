package com.example.gestionpedidofxml.domain.item;

import com.example.gestionpedidofxml.domain.DAO;
import com.example.gestionpedidofxml.domain.HibernateUtil;
import com.example.gestionpedidofxml.domain.orders.Pedido;
import com.example.gestionpedidofxml.domain.products.Producto;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.criteria.ParameterExpression;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemDAO implements DAO<Item> {


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

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Item nuevoItem = session.get(Item.class, data.getId());
            Producto nuevoProducto = session.get(Producto.class, data.getProducto().getId());
            nuevoItem.setCantidad(data.getCantidad());
            nuevoItem.setProducto(nuevoProducto);
            transaction.commit();
        }
    }

    @Override
    public void delete(Item data) {
        HibernateUtil.getSessionFactory().inTransaction( session -> {
            Item item = session.get(Item.class, data.getId());
            session.remove(item);
        });
    }

    public Item itemPedidoNombre(Pedido p, String nombreProducto){
        Item result = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Item> q = session.createQuery("from Item i where i.producto.nombre =: nombre and i.pedido.id=: idPedido", Item.class);
            q.setParameter("nombre", nombreProducto);
            q.setParameter("idPedido", p.getId());
            result = q.getSingleResultOrNull();
        }
        return result;
    }
}
