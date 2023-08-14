package com.svalero.cybershop.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name= "client")
public class Client {

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
       private String surname;

       @Column
       @PositiveOrZero(message = "<-- Este campo solo puede contener números positivos y 0")
       private int number;

       @Column
       @PastOrPresent(message = "<-- Este campo no admite fechas futuras, solo actuales o pasadas")
       private LocalDate registerDate;

       @Column
       private boolean vip;

       @OneToMany(mappedBy = "id")
       @JsonBackReference(value = "user-product")
       private List<Product> products;
}
