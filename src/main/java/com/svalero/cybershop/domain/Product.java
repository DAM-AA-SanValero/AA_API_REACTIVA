package com.svalero.cybershop.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import jakarta.validation.constraints.*;


import java.util.List;
import org.springframework.data.annotation.Id;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value= "product")
public class Product {

    @Id
    private String id;

    @Field
    @NotBlank(message = "<-- Este campo no puede estar vacio")
    @NotNull(message = "<-- Este campo es obligatorio")
    private String name;

    @Field
    @NotBlank(message = "<-- Este campo no puede estar vacio")
    @NotNull(message = "<-- Este campo es obligatorio")
    @Size(max = 20, message = "<-- Este campo solo puede tener 20 caracteres")
    private String type;

    @Field
    @NotNull(message = "<-- Este campo es obligatorio")
    @Positive(message = "<-- Este campo solo puede contener números positivos y es obligatorio")
    private float price;

    @Field
    private String origin;

    @Field
    private boolean inStock;
}
