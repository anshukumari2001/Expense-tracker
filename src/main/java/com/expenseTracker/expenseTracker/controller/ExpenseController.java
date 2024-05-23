package com.expenseTracker.expenseTracker.controller;

import com.expenseTracker.expenseTracker.entity.Expense;
import com.expenseTracker.expenseTracker.service.ExpenseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExpenseController {

    private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses(Pageable page) {
        return expenseService.getAllExpenses(page).toList();
    }

    @GetMapping("/expenses/pagination")
    public List<Expense> getAllExpensesPagingOther(
        @RequestParam(value = "pageNumber",
                      defaultValue = "1", required =
                          false) Integer pageNumber,
        @RequestParam(value = "pageSize",
                      defaultValue = "1", required =
                          false) Integer pageSize,
        @RequestParam(value = "sortBy", defaultValue = "property1", required = false) String sortBy,
        @RequestParam(value = "direction", defaultValue = "asc", required = false)
        String direction) {
        return expenseService.getAllExpensesPagination(pageNumber, pageSize, sortBy, direction);
    }

    @GetMapping("/expenses/{id}")
    public Expense getExpenseById(@PathVariable Long id) throws Exception {
        return expenseService.getExpenseById(id);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void deleteExpenseById(@RequestParam Long id) {
        expenseService.deleteExpenseById(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/expenses")
    public Expense saveExpense(@Valid @RequestBody Expense expense) {
        return expenseService.saveExpense(expense);
    }

    @PutMapping("/expenses/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense expense)
        throws Exception {
        return expenseService.updateExpense(id, expense);
    }

    @GetMapping("/expenses/category")
    public List<Expense> getExpenseByCategory(@RequestParam @NotNull String category,
                                              Pageable page) {
        return expenseService.getExpenseByCategory(category, page);
    }

    @GetMapping("/expenses/name")
    public List<Expense> getExpenseByName(@RequestParam @NotNull String name, Pageable page) {
        return expenseService.getExpenseByName(name, page);
    }

    @GetMapping("/expenses/date")
    public List<Expense> getExpenseByDateRange(@RequestParam @NotNull Date startDate,
                                               @RequestParam @NotNull Date endDate, Pageable page) {
        return expenseService.getExpenseByDateRange(startDate, endDate, page);
    }
}
