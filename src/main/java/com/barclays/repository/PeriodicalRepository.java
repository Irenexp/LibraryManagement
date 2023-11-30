package com.barclays.repository;

import com.barclays.model.Periodical;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PeriodicalRepository extends CrudRepository<Periodical, Integer> {
    List<Periodical> findByPublicationDate(LocalDate publicationDate);
    Optional<Periodical> findByTitle(String title);
}
