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