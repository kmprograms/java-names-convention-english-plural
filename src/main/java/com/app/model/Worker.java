package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Worker {
    private Integer id;
    private String name;
    private LocalDate birthDate;
    private String favouriteColor;
}
