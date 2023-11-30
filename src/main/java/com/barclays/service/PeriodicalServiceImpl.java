package com.barclays.service;

import com.barclays.model.Periodical;
import com.barclays.repository.PeriodicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodicalServiceImpl implements PeriodicalService {

    @Autowired
    private final PeriodicalRepository periodicalRepository;

    public PeriodicalServiceImpl(PeriodicalRepository periodicalRepository) {
        this.periodicalRepository = periodicalRepository;
    }


    @Override
    public List<Periodical> findAll(){
        List<Periodical> periodicals = new ArrayList<>();
        Iterable<Periodical> periodicalIts = periodicalRepository.findAll();
        periodicalIts.forEach((periodicals::add));
        return periodicals;
    }

    @Override
    public List<Periodical> findPeriodicalByDate(LocalDate date) {
        return periodicalRepository.findByPublicationDate(date);
    }

    @Override
    public Optional<Periodical> findByTitle(String title){
        return periodicalRepository.findByTitle(title);
    }

    @Override
    public Periodical savePeriodical(Periodical periodical) {
        return periodicalRepository.save(periodical);
    }

    @Override
    public void deletePeriodical(Periodical periodical) {
        periodicalRepository.delete(periodical);
    }

    @Override
    public Optional<Periodical> findPeriodicalById(Integer id) {
        return periodicalRepository.findById(id);
    }
}
