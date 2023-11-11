package com.example.gestionpedidofxml.domain.item;

import com.example.gestionpedidofxml.domain.products.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que presenta un item en la ap`licacion de pedidos
 * Esta clase almacena información sobre el ítem, incluyendo su identificación, código de pedido al que pertenece, producto asociado y cantidad.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    /**
     * Identificador unico del item
     */
    private Integer id;

    /**
     * Codigo de pedido al que pertenece el item
     */
    private String codigo_pedido;

    /**
     * Producto asociado con el item
     */
    private Producto producto_id;

    /**
     * Cantidad de productos en el item
     */
    private Integer cantidad;
}
