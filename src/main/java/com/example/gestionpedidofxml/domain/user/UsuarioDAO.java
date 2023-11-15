package com.example.gestionpedidofxml.domain.user;

import com.example.gestionpedidofxml.domain.DAO;
import com.example.gestionpedidofxml.domain.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class UsuarioDAO implements DAO<Usuario> {
    @Override
    public ArrayList<Usuario> getAll() {
        var salida = new ArrayList<Usuario>(0);

        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()){
            Query<Usuario> q = s.createQuery("from Usuario", Usuario.class);
            salida = (ArrayList<Usuario>) q.getResultList();
        }

        return salida;
    }

    @Override
    public Usuario get(Long id) {
        var salida = new Usuario();

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            salida = session.get(Usuario.class, id);
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

    public Usuario validateUser (String email, String pass){
        Usuario result = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Usuario> q = session.createQuery("from Usuario where email=:u and contrasenya=:p", Usuario.class);
            q.setParameter("u", email);
            q.setParameter("p", pass);

            try {
                result = q.getSingleResult();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }
}
