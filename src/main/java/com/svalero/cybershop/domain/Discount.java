package com.svalero.cybershop.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name= "discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank(message = "<-- Este campo no puede estar vacio")
    @NotNull(message = "<-- Este campo es obligatorio")
    private String product;

    @Column
    @Size(max = 500, message = "<-- Este campo solo puede tener 500 caracteres")
    @NotBlank(message = "<-- Este campo no puede estar vacio")
    @NotNull(message = "<-- Este campo es obligatorio")
    private String event;

    @Column
    @Negative(message = "<-- Este campo solo puede ser contener números negativos")
    private float discounted;

    @Column
    private LocalDate startDiscount;

    @Column
    private LocalDate endDiscount;


    @OneToMany(mappedBy = "id")
    @JsonBackReference(value = "dicount-product")
    private List<Product> products;


}
