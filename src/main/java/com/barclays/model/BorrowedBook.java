package com.barclays.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class BorrowedBook {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "book_id")
    @JsonBackReference
    private Book book;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "reader_id")
    private Reader reader;

    private LocalDate borrowDate;
    private LocalDate returnDate;
}
