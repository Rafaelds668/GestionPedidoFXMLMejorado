package com.example.gestionpedidofxml.domain.user;


import com.example.gestionpedidofxml.domain.orders.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {

    private Long id;
    private String nombre;
    private String contrasenya;
    private String email;
    private ArrayList<Pedido> pedidos;

}
