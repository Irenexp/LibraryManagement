package com.barclays.service;

import com.barclays.model.Reader;
import com.barclays.repository.BorrowedBookRepository;
import com.barclays.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private final BorrowedBookRepository borrowedBookRepository;

    @Autowired
    private final ReaderRepository readerRepository;

    public ReaderServiceImpl(BorrowedBookRepository borrowedBookRepository, ReaderRepository readerRepository) {
        this.borrowedBookRepository = borrowedBookRepository;
        this.readerRepository = readerRepository;
    }

    @Override
    public List<Reader> findAll() {
        List<Reader> readers = new ArrayList<>();
        Iterable<Reader> readerIts = readerRepository.findAll();
        readerIts.forEach((readers::add));
        return readers;
    }

    @Override
    public Optional<Reader> findReaderById(Integer id) {
        return readerRepository.findById(id);
    }

    @Override
    public Reader saveReader(Reader reader) {return readerRepository.save(reader);
    }

    @Override
    public void deleteReaderById(Integer id) {
        readerRepository.deleteById(id);
    }

}