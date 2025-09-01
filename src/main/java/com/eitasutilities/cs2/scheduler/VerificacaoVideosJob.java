package com.eitasutilities.cs2.scheduler;

import com.eitasutilities.cs2.entities.Utilitario;
import com.eitasutilities.cs2.repositories.UtilitarioRepository;
import com.eitasutilities.cs2.validator.YoutubeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VerificacaoVideosJob {

    private final UtilitarioRepository repository;

    private final YoutubeValidator youtubeValidator;

    @Scheduled(cron = "0 0 */4 * * *")
    public void verificarVideosDisponiveis() {
        List<Utilitario> paraSalvar = new ArrayList<>();
        List<Utilitario> utilitarios = repository.findAll();

        for (Utilitario utilitario : utilitarios) {
            boolean videoValido = youtubeValidator.ehVideoValido(utilitario.getLink());

            if (!videoValido && utilitario.getVideoDisponivel()) {
                utilitario.setVideoDisponivel(false);
                paraSalvar.add(utilitario);
            } else if (videoValido && !utilitario.getVideoDisponivel()) {
                utilitario.setVideoDisponivel(true);
                paraSalvar.add(utilitario);
            }
        }

        if (!paraSalvar.isEmpty()) {
            repository.saveAll(paraSalvar);
        }
    }
}
