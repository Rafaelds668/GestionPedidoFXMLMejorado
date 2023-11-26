package com.example.gestionpedidofxml.domain.user;

import com.example.gestionpedidofxml.domain.DAO;
import com.example.gestionpedidofxml.domain.HibernateUtil;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class UsuarioDAO implements DAO<Usuario> {
    /**
     * Obtiene todos los registros de usuarios almacenados en la base de datos.
     *
     * @return Una lista de usuarios que representa todos los registros de usuarios en la base de datos.
     */
    @Override
    public ArrayList<Usuario> getAll() {
        var salida = new ArrayList<Usuario>(0);

        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            Query<Usuario> q = s.createQuery("from Usuario ",Usuario.class);
            salida = (ArrayList<Usuario>) q.getResultList();
        }

        return salida;
    }

    /**
     * Obtiene un usuario de la base de datos según su identificador único.
     *
     * @param id Identificador único del usuario que se desea recuperar.
     * @return Un objeto de tipo Usuario que representa el usuario recuperado de la base de datos.
     */
    @Override
    public Usuario get(Long id) {
        var salida = new Usuario();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
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

    /**
     * Valida las credenciales de un usuario comparando el correo electrónico y la contraseña proporcionados con los
     * registros almacenados en la base de datos.
     *
     * @param email Correo electrónico del usuario que se desea validar.
     * @param pass Contraseña del usuario que se desea validar.
     * @return Un objeto de tipo Usuario si las credenciales son válidas; de lo contrario, devuelve null.
     */
    public Usuario validateUser (String email, String pass)  {
        //Desde un lambda no se puede escribir desde una variable externa.
        Usuario result = null;

        //Si la sesión está dentro de un try con recursos se cierra sola.
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            //Se hacen consultas a la entidad (clase User) no a la tabla.
            Query<Usuario> query = session.createQuery("from Usuario where email=:e and contrasenya=:p", Usuario.class);

            //Se refieren a los que entran por el método.
            query.setParameter("e", email);
            query.setParameter("p", pass);
            //

            try {
                result = query.getSingleResult();
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return result;
    }
}
