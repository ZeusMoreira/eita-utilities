package com.eitasutilities.cs2.repositories;

import com.eitasutilities.cs2.entities.Utilitario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtilitarioRepository extends JpaRepository<Utilitario, UUID> {
    Optional<Utilitario> findByLink(String link);

}
