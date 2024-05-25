package com.expenseTracker.expenseTracker.service;

import com.expenseTracker.expenseTracker.entity.Expense;
import com.expenseTracker.expenseTracker.exception.ResourceNotFoundException;
import com.expenseTracker.expenseTracker.repository.ExpenseRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserService userService;

    public ExpenseService(ExpenseRepository expenseRepository, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.userService = userService;
    }

    public Page<Expense> getAllExpenses(Pageable page) {
        return expenseRepository.findByUserId(userService.getLogdInUser().getId(), page);
    }

    public Expense getExpenseById(Long id) {
        Optional<Expense> expense =
            expenseRepository.findByUserIdAndId(userService.getLogdInUser().getId(), id);
        if(expense.isPresent()){
            return expense.get();
        }
        throw new ResourceNotFoundException("Expense is not found for given id : "+id);
    }

    public void deleteExpenseById(Long id) {
        expenseRepository.deleteById(id);
    }

    public Expense saveExpense(Expense expense) {
        expense.setUser(userService.getLogdInUser());
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense expense) throws Exception {
        Expense existingExpense = getExpenseById(id);
        existingExpense.setAmount(expense.getAmount()!=null ? expense.getAmount() : existingExpense.getAmount());
        existingExpense.setName(expense.getName()!=null ? expense.getName() : existingExpense.getName());
        existingExpense.setDate(expense.getDate()!=null ? expense.getDate() : existingExpense.getDate());
        existingExpense.setDescription(expense.getDescription()!=null ? expense.getDescription() : existingExpense.getDescription());
        existingExpense.setCategory(expense.getCategory()!=null ? expense.getCategory() : existingExpense.getCategory());
        return expenseRepository.save(existingExpense);
    }

    public List<Expense> getAllExpensesPagination(Integer pageNumber, Integer pageSize,
                                                  String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable p= PageRequest.of(pageNumber, pageSize, sort);
        Page<Expense> all = expenseRepository.findAll(p);
        return all.getContent();
    }

    public List<Expense> getExpenseByCategory(String category, Pageable page) {
        return expenseRepository.findByUserIdAndCategory(userService.getLogdInUser().getId(),
                                                         category,
                                                         page).toList();
    }

    public List<Expense> getExpenseByName(String name, Pageable page) {
        return expenseRepository.findByUserIdAndNameContaining(userService.getLogdInUser().getId(), name, page).toList();
    }

    public List<Expense> getExpenseByDateRange(Date startDate, Date endDate, Pageable page) {
        return expenseRepository.findByUserIdAndDateBetween(userService.getLogdInUser().getId(),
                                                            startDate, endDate, page).getContent();
    }
}
