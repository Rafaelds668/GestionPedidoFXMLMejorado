package com.example.gestionpedidofxml.domain.orders;

import com.example.gestionpedidofxml.domain.item.Item;
import com.example.gestionpedidofxml.domain.user.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "pedido")
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="codigo")
    private String codigo;

    @Column(name="fecha")
    private String fecha;

    @ManyToOne
    @JoinColumn (name = "usuario")
    private Usuario usuario;

    @Column(name="total")
    private Long total;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<Item> items;

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", fecha='" + fecha + '\'' +
                ", usuario=" + usuario.getNombre() +
                ", total=" + total +
                ", items=" + items +
                '}';
    }
}
