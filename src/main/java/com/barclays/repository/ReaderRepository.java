package com.barclays.repository;

import com.barclays.model.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReaderRepository extends CrudRepository<Reader,Integer> {
    List<Reader> findByName(String name);
}
