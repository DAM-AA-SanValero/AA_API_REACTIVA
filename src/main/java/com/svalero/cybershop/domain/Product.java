package com.svalero.cybershop.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name= "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank(message = "<-- Este campo no puede estar vacio")
    @NotNull(message = "<-- Este campo es obligatorio")
    private String name;

    @Column
    @NotBlank(message = "<-- Este campo no puede estar vacio")
    @NotNull(message = "<-- Este campo es obligatorio")
    @Size(max = 20, message = "<-- Este campo solo puede tener 20 caracteres")
    private String type;

    @Column
    @NotNull(message = "<-- Este campo es obligatorio")
    @Positive(message = "<-- Este campo solo puede contener números positivos y es obligatorio")
    private float price;

    @Column
    private String origin;

    @Column
    private boolean inStock;

    @OneToMany(mappedBy = "id")
    @JsonBackReference(value = "product-repairs")
    private List<Repair> repairs;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client_id;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount_id;




}
