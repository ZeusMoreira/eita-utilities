package com.eitasutilities.cs2.controller.dto;

import com.eitasutilities.cs2.entities.Utilitario;
import com.eitasutilities.cs2.entities.enums.Dificuldade;
import com.eitasutilities.cs2.entities.enums.Lado;
import com.eitasutilities.cs2.entities.enums.Tipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UtilitarioDTO(
    UUID id,
    @NotBlank
    @Size(max = 20, message = "O campo deve possuir no máximo 20 caracteres.")
    String lado,
    @NotBlank
    @Size(max = 20, message = "O campo deve possuir no máximo 20 caracteres.")
    String mapa,
    @NotBlank
    @Size(max = 2083, message = "O campo deve possuir no máximo 20 caracteres.")
    String link,
    @NotBlank
    @Size(max = 20, message = "O campo deve possuir no máximo 20 caracteres.")
    String tipo,
    @NotBlank
    @Size(max = 100, message = "O campo deve possuir no máximo 20 caracteres.")
    String titulo,
    @NotBlank
    @Size(max = 20, message = "O campo deve possuir no máximo 20 caracteres.")
    String dificuldade
) {
    public Utilitario mapearParaUtilitario() {
        Utilitario utilitario = new Utilitario();
        utilitario.setLado(Lado.valueOf(lado.toUpperCase()));
        utilitario.setMapa(mapa);
        utilitario.setLink(link);
        utilitario.setTipo(Tipo.valueOf(tipo.toUpperCase()));
        utilitario.setTitulo(titulo);
        utilitario.setDificuldade(Dificuldade.valueOf(dificuldade.toUpperCase()));
        return utilitario;
    }
    public UtilitarioDTO mapearParaUtilitarioDTO(Utilitario utilitario) {
        return new UtilitarioDTO(
                utilitario.getId(),
                utilitario.getLado().toString(),
                utilitario.getMapa(),
                utilitario.getLink(),
                utilitario.getTipo().toString(),
                utilitario.getTitulo(),
                utilitario.getDificuldade().toString()
        );
    }

}

