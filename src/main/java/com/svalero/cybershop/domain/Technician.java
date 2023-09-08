package com.svalero.cybershop.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import org.springframework.data.annotation.Id;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value= "technician")
public class Technician {

    @Id
    private String id;

    @Field
    @NotBlank(message = "<-- Este campo no puede estar vacio")
    @NotNull(message = "<-- Este campo es obligatorio")
    private String name;

    @Field
    @NotBlank(message = "<-- Este campo no puede estar vacio")
    @NotNull(message = "<-- Este campo es obligatorio")
    private String surname;

    @Field
    @PositiveOrZero(message = "<-- Este campo solo puede contener números positivos y 0")
    private int number;

    @Field
    @NotNull(message = "<-- Este campo es obligatorio")
    private String department;

    @Field
    private boolean available;



}
