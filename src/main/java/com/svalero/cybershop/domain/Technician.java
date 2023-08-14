package com.svalero.cybershop.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name= "techician")
public class Technician {

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
    @NotNull(message = "<-- Este campo es obligatorio")
    private String department;

    @Column
    private boolean available;

    @OneToMany(mappedBy = "id")
    @JsonBackReference(value = "technician-repair")
    private List<Repair> repairs;


}
