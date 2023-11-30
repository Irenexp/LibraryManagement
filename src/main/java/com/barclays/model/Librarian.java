package com.barclays.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Librarian {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String phoneNumber;
    private String email;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;
}
