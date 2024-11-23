package com.example.graphqltp.DTO;

import com.example.graphqltp.entities.Compte;
import com.example.graphqltp.entities.TypeTransaction;
import lombok.Data;

import java.util.Date;
import java.util.Optional;

@Data
public class TransactionRequest {
    private Long compteId;
    private double montant;
    private Date date;
    private TypeTransaction type;
}