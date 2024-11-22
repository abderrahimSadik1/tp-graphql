package com.example.graphqltp.repositories;

import com.example.graphqltp.entities.Compte;
import com.example.graphqltp.entities.TypeCompte;
import org.springframework.data.jpa.repository. JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
    List<Compte> findByType(TypeCompte type);
}