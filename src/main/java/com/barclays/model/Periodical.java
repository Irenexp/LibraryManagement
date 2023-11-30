package com.barclays.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@DiscriminatorValue("Periodical")
public class Periodical extends LendableMaterial{
    @Id
    @GeneratedValue
    private Integer id;

    private LocalDate publicationDate;

}
