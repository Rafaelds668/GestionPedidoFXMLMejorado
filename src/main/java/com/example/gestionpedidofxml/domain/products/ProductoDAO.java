package com.example.gestionpedidofxml.domain.products;

import com.example.gestionpedidofxml.domain.DAO;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.example.gestionpedidofxml.domain.HibernateUtil;

import java.util.ArrayList;

public class ProductoDAO implements DAO<Producto> {
    @Override
    public ArrayList<Producto> getAll() {
        var salida = new ArrayList<Producto>(0);
        try(Session sesion = HibernateUtil.getSessionFactory().openSession()){
            Query<Producto> query = sesion.createQuery(" from Producto ", Producto.class);
            salida = (ArrayList<Producto>) query.getResultList();
        }
        return salida;
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

}
