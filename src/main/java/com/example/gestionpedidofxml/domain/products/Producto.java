package com.example.gestionpedidofxml.domain.products;

import com.example.gestionpedidofxml.domain.item.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "producto")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="precio")
    private Double precio;

    @Column(name="cantidad_disponible")
    private Integer cantidadDisponible;

    @OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
    private List<Item> items;

}
