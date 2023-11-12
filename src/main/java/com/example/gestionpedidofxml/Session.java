package com.example.gestionpedidofxml;

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
}
