package com.plandoer.rental;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Rental {
    private final Long id;
    private final String userId;
    private final Long reservationId;
    private final LocalDate startDate;
}
