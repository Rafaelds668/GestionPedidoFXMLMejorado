package com.example.gestionpedidofxml.domain.item;

import com.example.gestionpedidofxml.domain.orders.Pedido;
import com.example.gestionpedidofxml.domain.products.Producto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Clase que presenta un item en la ap`licacion de pedidos
 * Esta clase almacena información sobre el ítem, incluyendo su identificación, código de pedido al que pertenece, producto asociado y cantidad.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "item")
public class Item implements Serializable {
    /**
     * Identificador unico del item
     */
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Codigo de pedido al que pertenece el item
     */

    @ManyToOne
    @JoinColumn (name = "codigo_pedido", referencedColumnName = "codigo")
    private Pedido pedido;

    /**
     * Producto asociado con el item
     */
    @ManyToOne()
    @JoinColumn(name="producto_id")
    private Producto producto;

    /**
     * Cantidad de productos en el item
     */
    @Column (name = "cantidad")
    private Integer cantidad;

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", pedido=" + pedido.getCodigo() +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                '}';
    }
}
