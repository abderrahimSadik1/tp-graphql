package com.example.graphqltp.controllers;

import com.example.graphqltp.entities.Compte;
import com.example.graphqltp.entities.Transaction;
import com.example.graphqltp.entities.TypeCompte;
import com.example.graphqltp.repositories.CompteRepository;
import com.example.graphqltp.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class CompteControllerGraphQL {
    private CompteRepository compteRepository;
    private TransactionRepository transactionRepository;

    @QueryMapping
    public List<Compte> allComptes(){ return compteRepository.findAll();
    }
    @QueryMapping
    public Compte compteById(@Argument Long id) {
        Compte compte = compteRepository.findById(id).orElse (null);
        if (compte == null) throw new RuntimeException(String.format("Compte %s not found", id));
        else return compte;
    }

    @QueryMapping
    public List<Compte> compteByType(@Argument TypeCompte type){
        return compteRepository.findByType(type);
    }

    @MutationMapping
    public Transaction addTransaction(@Argument Transaction transactionRequest) {
        Compte compte = compteRepository.findById(transactionRequest.getCompte().getId()).orElseThrow(() -> new RuntimeException("Compte not found"));
        Transaction transaction = new Transaction();
        transaction.setMontant(transactionRequest.getMontant());
        transaction.setDate(transactionRequest.getDate());
        transaction.setType(transactionRequest.getType());
        transaction.setCompte(compte);
        transactionRepository.save(transaction);
        return transaction;
    }

    @QueryMapping
    public void deleteById(@Argument Long id){
        compteRepository.deleteById(id);
    }

    @MutationMapping
    public Compte saveCompte (@Argument Compte compte) {
        return compteRepository.save(compte);
    }


    @QueryMapping
    public Map<String, Object> totalSolde() {
        long count = compteRepository.count();
        double sum = compteRepository.count();
        double average = count > 0 ? sum / count : 0;
        return Map.of("count", count, "sum", sum, "average", average);
    }
}