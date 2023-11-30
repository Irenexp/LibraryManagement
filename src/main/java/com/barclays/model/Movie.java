package com.barclays.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("Movie")
public class Movie extends LendableMaterial{
    @Id
    @GeneratedValue
    private Integer id;

    private String director;
    private String screenWriter;
    private LocalDate releaseDate;
    private String genre;
    private Integer rating;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LeadActor> leadActorList;


}
