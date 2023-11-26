package com.example.gestionpedidofxml.domain.orders;

import com.example.gestionpedidofxml.domain.DAO;
import com.example.gestionpedidofxml.domain.HibernateUtil;
import com.example.gestionpedidofxml.domain.item.Item;
import com.example.gestionpedidofxml.domain.products.Producto;
import com.example.gestionpedidofxml.domain.user.Usuario;
import lombok.extern.java.Log;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log
public class PedidoDAO implements DAO<Pedido> {

    /**
     * Obtiene todos los registros de pedido almacenados en la base de datos.
     *
     * @return Una lista de pedidos que representa todos los registros de pedidos en la base de datos.
     */
    @Override
    public ArrayList<Pedido> getAll() {
        var salida = new ArrayList<Pedido>(0);
        try(Session sesion = HibernateUtil.getSessionFactory().openSession()){
            Query<Pedido> query = sesion.createQuery("from Pedido ", Pedido.class);
            salida = (ArrayList<Pedido>) query.getResultList();
        }
        return salida;
    }

    /**
     * Obtiene un pedido de la base de datos según su identificador único.
     *
     * @param id Identificador único del pedio que se desea recuperar.
     * @return Un objeto de tipo Pedido que representa el ítem recuperado de la base de datos.
     */
    @Override
    public Pedido get(Long id) {
        var salida = new Pedido();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            salida = session.get(Pedido.class, id);
        }
        return salida;
    }

    /**
     * Guarda un nuevo pedido en la base de datos. Si el pedido ya tiene un identificador único,
     * se considera una actualización; de lo contrario, se trata de un nuevo pedido que se persiste en la base de datos.
     *
     * @param data El pedido que se desea guardar en la base de datos.
     * @return Un objeto de tipo Pedido que representa el pedido guardado en la base de datos.
     */
    @Override
    public Pedido save(Pedido data) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                // Comenzar la transacción
                transaction = session.beginTransaction();

                // Guardar el nuevo pedido en la base de datos
                session.save(data);

                // Commit de la transacción
                transaction.commit();
            } catch (Exception e) {
                // Manejar excepción que pueda ocurrir durante la transacción
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
            return data;
        }
    }

    /**
     * Actualiza un pedido existente en la base de datos.
     *
     * @param data El pedido que se desea actualizar en la base de datos.
     */
    @Override
    public void update(Pedido data) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Actualizar el pedido en la base de datos
            session.update(data);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Elimina un pedido de la base de datos.
     *
     * @param data El pedido que se desea eliminar de la base de datos.
     */
    @Override
    public void delete(Pedido data) {
        HibernateUtil.getSessionFactory().inTransaction((session -> {
            Pedido o = session.get(Pedido.class, data.getId());
            session.remove(o);
        }));
    }
}
