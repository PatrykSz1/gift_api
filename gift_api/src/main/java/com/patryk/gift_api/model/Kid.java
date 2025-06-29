package com.patryk.gift_api.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public class Kid implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    @Version
    private int version;

    @OneToMany(mappedBy = "kid", orphanRemoval = true)
    private List<Gift> gifts = new ArrayList<>();

    @Override
    public Kid clone() {
        try {
            return (Kid) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("error while cloning Kid", e);
        }
    }
}
