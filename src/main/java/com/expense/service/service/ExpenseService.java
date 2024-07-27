package com.expense.service.service;

import com.expense.service.DTO.ExpenseDTO;
import com.expense.service.Entity.Expense;
import com.expense.service.Repository.ExpenseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ExpenseService(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    public  boolean createExpense(ExpenseDTO expenseDTO){
        setCurrency(expenseDTO);
        try{
            expenseRepository.save(objectMapper.convertValue(expenseDTO, Expense.class));
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public boolean updateExpense(ExpenseDTO expenseDTO) {
        setCurrency(expenseDTO);
        Optional<Expense> expenseFoundOpt = expenseRepository.findByUserIdAndExternalId(expenseDTO.getUserId(),
                expenseDTO.getExternalId());
        if(expenseFoundOpt.isEmpty()) return false;
        Expense expense = expenseFoundOpt.get();
        expense.setAmount(expenseDTO.getAmount());
        expense.setCurrency(Strings.isNotBlank(expenseDTO.getCurrency()) ? expenseDTO.getCurrency():
                expense.getCurrency());
        expense.setMerchant(Strings.isNotBlank(expenseDTO.getMerchant()) ? expenseDTO.getMerchant() : expense.getMerchant());
        expenseRepository.save(expense);
        return true;
    }

    public List<ExpenseDTO> getAllExpense(String userId){
        List<Expense> expenseList = expenseRepository.findByUserId(userId);
        return objectMapper.convertValue(expenseList,
                new TypeReference<List<ExpenseDTO>>() {});
    }

    private void setCurrency(ExpenseDTO expenseDTO){
        if(Objects.isNull(expenseDTO.getCurrency())){
            expenseDTO.setCurrency("inr");
        }
    }
}
