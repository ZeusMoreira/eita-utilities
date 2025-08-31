package com.eitasutilities.cs2.validator;

import com.eitasutilities.cs2.exceptions.LinkInvalidoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.eitasutilities.cs2.utils.YouTubeUtils;

@Component
public class YoutubeValidator {

    @Value("${youtube.api-key}")
    private String youtubeApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void validarVideoExistente(String url) {
        if(!ehVideoValido(url)) {
            throw new LinkInvalidoException("O vídeo informado não existe ou não está disponível.");
        }
    }
    public boolean ehVideoValido(String url) {
        String videoId = YouTubeUtils.extrairId(url);
        if (videoId == null) {
            return false;
        }

        try {
            String apiUrl = "https://www.googleapis.com/youtube/v3/videos?part=status&id="
                    + videoId + "&key=" + youtubeApiKey;
            String response = restTemplate.getForObject(apiUrl, String.class);

            JsonNode root = objectMapper.readTree(response);

            return !root.path("items").isEmpty();
        } catch (Exception e) {
            throw new LinkInvalidoException("Erro ao validar o vídeo do YouTube: " + e.getMessage());
        }
    }
}
