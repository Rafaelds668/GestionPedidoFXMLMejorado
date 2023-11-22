package com.example.gestionpedidofxml.domain.products;

import com.example.gestionpedidofxml.domain.DAO;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.example.gestionpedidofxml.domain.HibernateUtil;

import java.util.ArrayList;

public class ProductoDAO implements DAO<Producto> {
    @Override
    public ArrayList<Producto> getAll() {
        return null;
    }

    @Override
    public Producto get(Long id) {
        return null;
    }

    @Override
    public Producto save(Producto data) {
        return null;
    }

    @Override
    public void update(Producto data) {

    }

    @Override
    public void delete(Producto data) {

    }

    public Producto productoPorNombre(String nombreProducto){
        Producto result = null;
        try(Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Producto> q = s.createQuery("from Producto p where p.nombre =: n",Producto.class);
            q.setParameter("n",nombreProducto);
            result = q.getSingleResultOrNull();
        }
        return result;
    }
}
