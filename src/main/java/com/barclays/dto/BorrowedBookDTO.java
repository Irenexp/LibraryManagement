package com.barclays.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class BorrowedBookDTO {
    private Integer id;
    private Integer bookId;
    private Integer readerId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
}
