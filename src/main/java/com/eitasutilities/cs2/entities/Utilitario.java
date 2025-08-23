package com.eitasutilities.cs2.entities;

import com.eitasutilities.cs2.entities.enums.Dificuldade;
import com.eitasutilities.cs2.entities.enums.Lado;
import com.eitasutilities.cs2.entities.enums.Tipo;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "utilitario", schema = "eita_utilities")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Utilitario {

    @Id
    @Column(name = "ut_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "ut_lado", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Lado lado;

    @Column(name = "ut_mapa", length = 20, nullable = false)
    private String mapa;

    @Column(name = "ut_link", length = 2083, nullable = false)
    private String link;

    @Column(name = "ut_tipo", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Column(name = "ut_titulo", length = 100, nullable = false)
    private String titulo;

    @Column(name = "ut_dificuldade", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Dificuldade dificuldade;

    @CreatedDate
    @Column(name = "ut_data_criacao")
    private LocalDateTime dataCriacao;

}
