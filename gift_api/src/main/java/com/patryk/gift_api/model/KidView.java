package com.patryk.gift_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

@Entity
@Table(name = "kid_view")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class KidView {

    @Id
    private int id;

    private String dtype;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String colorOfShirt;
    private String colorOfDress;
    private int giftCount;
}
