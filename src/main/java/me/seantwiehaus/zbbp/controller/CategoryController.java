package me.seantwiehaus.zbbp.controller;

import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.domain.BudgetMonth;
import me.seantwiehaus.zbbp.domain.BudgetMonthRange;
import me.seantwiehaus.zbbp.dto.response.CategoryResponse;
import me.seantwiehaus.zbbp.exception.NotFoundException;
import me.seantwiehaus.zbbp.service.CategoryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class CategoryController {
    CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    /**
     * @param startBudgetDate Include all Categories with budgetDates greater-than-or-equal to this BudgetDate.
     *                        BudgetDates are always on the 1st day of the month.
     *                        If no value is supplied, the default value will be the first day of the current month
     *                        100 years in the past.
     * @param endBudgetDate   Include all Categories with budgetDates less-than-or-equal-to this BudgetDate.
     *                        BudgetDates are always on the 1st day of the month.
     *                        If no value is supplied, the default value will be the first day of the current month
     *                        100 years in the future.
     * @return All Categories with BudgetDates between the startBudgetDate and endBudgetDate (inclusive).
     */
    @GetMapping("/categories")
    public List<CategoryResponse> getAllCategoriesBetween(@RequestParam
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                          Optional<LocalDate> startBudgetDate,
                                                          @RequestParam
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                          Optional<LocalDate> endBudgetDate) {
        BudgetMonthRange budgetMonthRange = new BudgetMonthRange(
                startBudgetDate.map(BudgetMonth::new).orElse(null),
                endBudgetDate.map(BudgetMonth::new).orElse(null));
        return service.getAllCategoriesBetween(budgetMonthRange)
                .map(CategoryResponse::new)
                .toList();
    }

    @GetMapping("/category/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return service.findCategoryById(id)
                .map(CategoryResponse::new)
                .orElseThrow(() -> new NotFoundException("Unable to locate Category with ID: " + id));
    }

    @PostMapping("/category")
    public CategoryResponse createCategory(@RequestBody @Valid CategoryResponse categoryResponseDto) {
        return new CategoryResponse(service.create(categoryResponseDto.convertToCategory()));
    }
}
