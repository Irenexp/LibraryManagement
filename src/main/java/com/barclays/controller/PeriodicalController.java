package com.barclays.controller;

import com.barclays.model.Periodical;
import com.barclays.service.PeriodicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/periodicals")
public class PeriodicalController {
    private final PeriodicalService periodicalService;

    @Autowired
    public PeriodicalController(PeriodicalService periodicalService) {
        this.periodicalService = periodicalService;
    }

    @GetMapping
    public ResponseEntity<List<Periodical>> getAllPeriodicals() {
        List<Periodical> periodicals = periodicalService.findAll();
        return ResponseEntity.ok(periodicals);
    }

    @GetMapping("/searchByTitle")
    public ResponseEntity<Periodical> getPeriodicalByTitle(@RequestParam String title) {
        Optional<Periodical> periodical = periodicalService.findByTitle(title);
        return periodical.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/searchByDate")
    public ResponseEntity<List<Periodical>> getPeriodicalsByDate(@RequestParam LocalDate date) {
        List<Periodical> periodicals = periodicalService.findPeriodicalByDate(date);
        return ResponseEntity.ok(periodicals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Periodical> getPeriodicalById(@PathVariable Integer id) {
        Optional<Periodical> periodical = periodicalService.findPeriodicalById(id);
        return periodical.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Periodical> createPeriodical(@RequestBody Periodical periodical) {
        Periodical savedPeriodical = periodicalService.savePeriodical(periodical);
        return ResponseEntity.ok(savedPeriodical);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Periodical> updatePeriodical(@PathVariable Integer id, @RequestBody Periodical periodicalDetails) {
        Optional<Periodical> periodicalOptional = periodicalService.findPeriodicalById(id);
        if (periodicalOptional.isPresent()) {
            Periodical periodicalToUpdate = periodicalOptional.get();

            periodicalToUpdate.setTitle(periodicalDetails.getTitle());
            periodicalToUpdate.setPublicationDate(periodicalDetails.getPublicationDate());

            Periodical updatedPeriodical = periodicalService.savePeriodical(periodicalToUpdate);
            return ResponseEntity.ok(updatedPeriodical);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeriodical(@PathVariable Integer id) {
        Optional<Periodical> periodicalOptional = periodicalService.findPeriodicalById(id);
        if (periodicalOptional.isPresent()) {
            periodicalService.deletePeriodical(periodicalOptional.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
