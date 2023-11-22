package com.example.gestionpedidofxml.domain;

import com.example.gestionpedidofxml.domain.item.Item;
import com.example.gestionpedidofxml.domain.orders.Pedido;
import com.example.gestionpedidofxml.domain.user.Usuario;
import lombok.Getter;
import lombok.Setter;

public class Session {
    @Getter
    @Setter
    private static Usuario currentUser;

    @Getter
    @Setter
    private static Pedido currentOrder;

    @Getter
    @Setter
    private static Item currentItem;

    @Getter
    @Setter
    private static boolean esUnNuevoProducto;

    @Getter
    @Setter
    private static boolean esNuevoPedido;

    @Getter
    @Setter
    private static Pedido pedidoPulsado;

    @Getter
    @Setter
    private static Item itemPulsado;
}
