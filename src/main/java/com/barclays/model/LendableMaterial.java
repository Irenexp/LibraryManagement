package com.barclays.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class LendableMaterial {
    @Id
    @GeneratedValue
    Integer id;

    private String title;

}
