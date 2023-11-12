package com.example.gestionpedidofxml.domain.orders;

import com.example.gestionpedidofxml.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido implements Serializable {

    private Long id;
    private String codigo;
    private String fecha;
    private Long usuarioId;
    private Long total;
    private ArrayList <Item> items = new ArrayList<>();
}
