package com.barclays.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue
    private Integer id;

    private String lineOne;
    private String lineTwo;
    private String state;
    private String postalCode;
    private String country;

    public Address(String lineOne, String lineTwo, String state, String postalCode, String country) {
        this.lineOne = lineOne;
        this.lineTwo = lineTwo;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }
}
