package org.example;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="detalle_factura")
public class DetalleFactura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int cantidad;
    @Column
    private int subtotal;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="fk_factura")
    private Factura factura;



}
