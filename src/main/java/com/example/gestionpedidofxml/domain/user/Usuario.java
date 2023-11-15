package com.example.gestionpedidofxml.domain.user;


import com.example.gestionpedidofxml.domain.orders.Pedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "contrasenya")
    private String contrasenya;

    @Column(name = "email")
    private String email;

    @Transient
    private Long pedidosQuantity;

    @OneToMany (mappedBy = "usuario", fetch = FetchType.EAGER)
   private List<Pedido> pedidos = new ArrayList<>();

    public Long getPedidosQuantity(){
        pedidosQuantity = (long) pedidos.size();
        return pedidosQuantity;
    }

}
