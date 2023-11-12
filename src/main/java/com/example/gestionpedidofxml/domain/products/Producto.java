package com.example.gestionpedidofxml.domain.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto implements Serializable {

    private Integer id;
    private String nombre;
    private Double precio;
    private Integer cantidad_disponible;
}
