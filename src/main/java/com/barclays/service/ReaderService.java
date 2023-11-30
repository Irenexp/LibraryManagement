package com.barclays.service;

import com.barclays.model.Book;
import com.barclays.model.BorrowedBook;
import com.barclays.model.Reader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ReaderService {

    List<Reader> findAll();

    Optional<Reader> findReaderById(Integer id);

    Reader saveReader(Reader reader);

    void deleteReaderById(Integer id);
}


