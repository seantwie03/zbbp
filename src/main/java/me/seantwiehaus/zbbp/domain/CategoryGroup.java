package me.seantwiehaus.zbbp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryGroup {
    private final Long id;
    private final String name;
    private final LocalDate budgetDate;
    private final List<Category> categories;
}
