package com.barclays.service;

import com.barclays.model.Periodical;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PeriodicalService {
    List<Periodical> findAll();

    List<Periodical> findPeriodicalByDate(LocalDate date);

    Optional<Periodical> findByTitle(String title);

    Periodical savePeriodical(Periodical periodical);

    void deletePeriodical(Periodical periodical);

    Optional<Periodical> findPeriodicalById(Integer id);
}
