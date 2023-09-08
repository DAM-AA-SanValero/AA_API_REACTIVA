package com.svalero.cybershop.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import jakarta.validation.constraints.*;


import java.time.LocalDate;
import org.springframework.data.annotation.Id;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value= "repair")
public class Repair {

    @Id
    private String id;

    @Field
    @Size(max = 20, message = "<-- Este campo solo puede tener 20 caracteres")
    @NotBlank(message = "<-- Este campo no puede estar vacio")
    @NotNull(message = "<-- Este campo es obligatorio")
    private String component;

    @Field
    @NotNull(message = "<-- Este campo es obligatorio")
    @Positive(message = "<-- Este campo solo puede contener números positivos")
    private float price;

    @Field
    @Size(max = 500, message = "<-- Este campo solo puede tener 500 caracteres")
    private String shippingAddress;

    @Field
    private LocalDate shipmentDate;

    @Field
    @PastOrPresent(message = "<-- Este campo no admite fechas futuras, solo actuales o pasadas")
    private LocalDate repairedDate;

}
