package com.expense.service.controller;

import com.expense.service.DTO.ExpenseDTO;
import com.expense.service.service.ExpenseService;
import jakarta.websocket.server.PathParam;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/v1/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ExpenseDTO>> getAllExpense(@RequestParam("user_id") @NonNull String userId){
        try{
            List<ExpenseDTO> expenseDTOList = expenseService.getAllExpense(userId);
            return new ResponseEntity<>(expenseDTOList, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addExpense(@RequestHeader(value = "X-User-Id") @NonNull String userId,
                                              ExpenseDTO expenseDTO){
        try{
            expenseDTO.setUserId(userId);
            return new ResponseEntity<>(expenseService.createExpense(expenseDTO), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
