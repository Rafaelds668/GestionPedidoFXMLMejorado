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

    /**
     * Obtiene todos los registros de ítems almacenados en la base de datos.
     *
     * @return Una lista de ítems que representa todos los registros de ítems en la base de datos.
     */
    @Override
    public ArrayList<Item> getAll() {
        var salida = new ArrayList<Item>(0);
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Item> q = session.createQuery("from Item", Item.class);
            salida = (ArrayList<Item>) q.getResultList();
        }
        return salida;
    }
    /**
     * Obtiene un ítem de la base de datos según su identificador único.
     *
     * @param id Identificador único del ítem que se desea recuperar.
     * @return Un objeto de tipo Item que representa el ítem recuperado de la base de datos.
     */
    @Override
    public Item get(Long id) {
        var salida = new Item();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            salida = session.get(Item.class, id);
        }
        return salida;
    }

    /**
     * Guarda un nuevo ítem o actualiza uno existente en la base de datos. Si el ítem ya tiene un identificador único,
     * se considera una actualización; de lo contrario, se trata de un nuevo ítem que se persiste en la base de datos.
     *
     * @param data El ítem que se desea guardar o actualizar en la base de datos.
     * @return Un objeto de tipo Item que representa el ítem guardado o actualizado en la base de datos.
     */
    @Override
    public Item save(Item data) {
        try(org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction t = null;
            try {
                t = session.beginTransaction();
                session.persist(data);
                t.commit(); //Lo hago persistente por lo que se sincronizan los datos con la base de datos, lo que implica que el objeto se guarda y se modifica
            } catch (Exception e) {
                if (t != null){
                    t.rollback();
                }
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * Actualiza un ítem existente en la base de datos.
     *
     * @param data El ítem que se desea actualizar en la base de datos.
     */
    @Override
    public void update(Item data) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            //Dar comienzo a la transaccion
            transaction = session.beginTransaction();

            //Actualizo el item en la base de datos
            session.update(data);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    /**
     * Elimina un ítem de la base de datos.
     *
     * @param data El ítem que se desea eliminar de la base de datos.
     */
    @Override
    public void delete(Item data) {
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            Item item = session.get(Item.class, data.getId());
            session.remove(item);
        });
    }
}
