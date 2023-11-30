package com.barclays.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Reader {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String phoneNumber;
    private String email;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BorrowedBook> borrowedBooks = new ArrayList<>();
}
