package com.expense.service.consumer;

import com.expense.service.DTO.ExpenseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ExpenseDeserializer implements Deserializer<ExpenseDTO> {
    @Override
    public void close(){};

    @Override
    public void configure(Map<String, ?> arg0, boolean arg1){}

    @Override
    public ExpenseDTO deserialize(String arg0, byte[] arg1){
        ObjectMapper mapper = new ObjectMapper();
        ExpenseDTO expense = null;
        try{
            expense = mapper.readValue(arg1, ExpenseDTO.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return expense;
    }
}
