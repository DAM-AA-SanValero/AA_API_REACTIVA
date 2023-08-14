package com.svalero.cybershop.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value= "discount")
public class Discount {

    @Id
    private String id;

    @Field
    @NotBlank(message = "<-- Este campo no puede estar vacio")
    @NotNull(message = "<-- Este campo es obligatorio")
    private String product;

    @Field
    @Size(max = 500, message = "<-- Este campo solo puede tener 500 caracteres")
    @NotBlank(message = "<-- Este campo no puede estar vacio")
    @NotNull(message = "<-- Este campo es obligatorio")
    private String event;

    @Field
    @Negative(message = "<-- Este campo solo puede ser contener números negativos")
    private float discounted;

    @Field
    private LocalDate startDiscount;

    @Field
    private LocalDate endDiscount;
}
