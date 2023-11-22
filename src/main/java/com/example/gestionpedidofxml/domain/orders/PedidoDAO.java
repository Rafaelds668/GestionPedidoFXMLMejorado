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

        Pedido salida = null;
        try(org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction t = session.beginTransaction();
            session.persist(data);
            t.commit();
            salida = data;
        } catch (Exception e){
            log.severe("Error al guardar. " + data.toString());
        }
        return salida;
    }

    @Override
    public void update(Pedido data) {
    }

    @Override
    public void delete(Pedido data) {

        HibernateUtil.getSessionFactory().inTransaction(s ->{
            Query<Item> q = s.createQuery("from Item where pedido =: ped", Item.class);
            q.setParameter("ped", data);
            List<Item> items = q.getResultList();
            Pedido i = s.get(Pedido.class, data.getId());
            items.forEach(it -> s.remove(it));

            s.remove(i);

        });
    }

    public List<Pedido> pedidosUsuario(Usuario usuario){
        List<Pedido> salida = new ArrayList<>( );
        try ( Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Query<Usuario> q = s.createQuery( "from Usuario where id =: id" , Usuario.class );
            q.setParameter( "id" , usuario.getId( ) );
            salida = q.getSingleResult( ).getPedidos( );
        }
        for (Pedido pedido : salida) {
            Double total = 0.0;
            for (Item item : pedido.getItems( )) {
                total = total + item.getCantidad( ) * item.getProducto( ).getPrecio( );
            }
            DecimalFormat formato = new DecimalFormat( "#0.00" );
            pedido.setTotal( formato.format( total ) );
        }
        return salida;
    }

    public List<Item> detallesPedido(Pedido pedidoPulsado){
        List<Item> result;
        try ( Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Query<Pedido> q = s.createQuery( "from Pedido where id =: id" , Pedido.class );
            q.setParameter( "id" , pedidoPulsado.getId( ) );
            result = new ArrayList<>( q.getSingleResult( ).getItems( ) );
        }
        return result;
    }

    public List<String> todosLosProductos( ) {
        List<String> resultado = new ArrayList<String>( );
        try ( Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Query<String> q = s.createQuery( "select distinct p.nombre from Producto p" , String.class );
            resultado = q.getResultList( );
        }

        return resultado;
    }

    public void insertarItemPedido(Pedido pedido, Integer cant, Producto producto){
        try (org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            //Crear Nuevo item
            Item item = new Item();
            item.setCantidad(cant);
            item.setPedido(pedido);
            item.setProducto(producto);
            session.persist(item);
            transaction.commit();
        }catch (Exception e){
            log.severe("Error al insertar un nuevo item");
        }
    }

    public Producto buscaProductoEnPedido( String nombreProducto , Pedido pedido ) {
        Producto producto = null;
        try ( org.hibernate.Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Query<Producto> q = s.createQuery(
                    "select i.producto from Item i where i.producto.nombre =: nombre and i.pedido.id =: idPedido" , Producto.class );
            q.setParameter( "nombre" , nombreProducto );
            q.setParameter( "idPedido" , pedido.getId( ) );
            producto = q.getSingleResultOrNull( );
        }
        return producto;
    }

    public boolean estaProductoEnPedido( String nombreProducto , Pedido pedido ) {
        return buscaProductoEnPedido( nombreProducto , pedido ) != null;
    }

    public void actualizarFecha(Pedido ped){
        HibernateUtil.getSessionFactory().inTransaction(s -> {
            Pedido p = s.get( Pedido.class , ped.getId() );
            p.setFecha( LocalDate.now().toString() );
        });
    }
}
