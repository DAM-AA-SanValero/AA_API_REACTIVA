package com.svalero.cybershop.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name= "repair")
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Size(max = 20, message = "<-- Este campo solo puede tener 20 caracteres")
    @NotBlank(message = "<-- Este campo no puede estar vacio")
    @NotNull(message = "<-- Este campo es obligatorio")
    private String component;

    @Column
    @NotNull(message = "<-- Este campo es obligatorio")
    @Positive(message = "<-- Este campo solo puede contener números positivos")
    private float price;

    @Column
    @Size(max = 500, message = "<-- Este campo solo puede tener 500 caracteres")
    private String shippingAddress;

    @Column
    private LocalDate shipmentDate;

    @Column
    @PastOrPresent(message = "<-- Este campo no admite fechas futuras, solo actuales o pasadas")
    private LocalDate repairedDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product_id;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private Technician technician_id;





}
