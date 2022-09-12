package me.seantwiehaus.zbbp.controller;

import me.seantwiehaus.zbbp.dto.CategoryDto;
import me.seantwiehaus.zbbp.service.CategoryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {
    CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    /**
     * @param startBudgetDate Include all Categories with budgetDates greater-than-or-equal to this budgetDate.
     *                        BudgetDates are always on the 1st day of the month.
     *                        If no value is supplied, the default value will be the first day of the current month
     *                        100 years in the past.
     * @param endBudgetDate   Include all Categories with budgetDates less-than-or-equal-to this budgetDate.
     *                        BudgetDates are always on the 1st day of the month.
     *                        If no value is supplied, the default value will be the first day of the current month
     *                        100 years in the future.
     * @return All Categories with budgetDates between the start and end budgetDates (inclusive).
     */
    @GetMapping("/categories")
    public List<CategoryDto> getAllCategoriesBetween(@RequestParam
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                     Optional<LocalDate> startBudgetDate,
                                                     @RequestParam
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                     Optional<LocalDate> endBudgetDate) {
        YearMonth start = startBudgetDate
                .map(YearMonth::from)
                .orElse(YearMonth.from(LocalDate.now().minusYears(100)));
        YearMonth end = endBudgetDate
                .map(YearMonth::from)
                .orElse(YearMonth.from(LocalDate.now().plusYears(100)));
        return service.getAllCategoriesBetween(start, end).stream()
                .map(CategoryDto::new)
                .toList();
    }

    @GetMapping("/category/{id}")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return new CategoryDto(service.findCategoryById(id));
    }

}