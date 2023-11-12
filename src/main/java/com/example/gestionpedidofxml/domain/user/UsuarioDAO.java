package com.example.gestionpedidofxml.domain.user;

import com.example.gestionpedidofxml.domain.DAO;
import com.example.gestionpedidofxml.domain.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class UsuarioDAO implements DAO<Usuario> {


    @Override
    public ArrayList<Usuario> loadAll(){
        var salida = new ArrayList<Usuario>(0);

        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            Query<Usuario> q = s.createQuery("from Usuario", Usuario.class);
            salida = (ArrayList<Usuario>) q.getResultList();
        }
        return null;
    }

    @Override
    public Usuario get(Long id) {
        var salida = new Usuario();
        try (Session s = HibernateUtil.getSessionFactory().openSession()){
            salida = s.get(Usuario.class, id);
        }
        return salida;
    }

    @Override
    public Usuario save(Usuario data) {
        return null;
    }

    @Override
    public void update(Usuario data) {

    }

    @Override
    public void delete(Usuario data) {

    }
}
