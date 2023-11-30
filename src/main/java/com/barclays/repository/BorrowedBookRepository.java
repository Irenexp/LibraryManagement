package com.barclays.repository;

import com.barclays.model.BorrowedBook;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BorrowedBookRepository extends CrudRepository<BorrowedBook, Integer> {
    List<BorrowedBook> findByReaderId(Integer readerId);
    List<BorrowedBook> findByReturnDateIsNull();

    boolean existsByBookIdAndReturnDateIsNull(Integer bookId);

    List<BorrowedBook> findByBookId(Integer bookId);
}
