package com.patryk.gift_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@ToString(exclude = "kid")
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private BigDecimal price;

    @Version
    private int version;

    @ManyToOne
    @JoinColumn(name = "kid_id", nullable = false)
    private Kid kid;
}
