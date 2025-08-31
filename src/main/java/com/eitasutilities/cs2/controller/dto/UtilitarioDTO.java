package com.eitasutilities.cs2.controller.dto;

import com.eitasutilities.cs2.entities.Utilitario;
import com.eitasutilities.cs2.entities.enums.Dificuldade;
import com.eitasutilities.cs2.entities.enums.Lado;
import com.eitasutilities.cs2.entities.enums.Tipo;
import com.eitasutilities.cs2.utils.YouTubeUtils;

import java.util.UUID;

public record UtilitarioDTO(
    UUID id,
    String lado,
    String mapa,
    String link,
    String tipo,
    String titulo,
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

