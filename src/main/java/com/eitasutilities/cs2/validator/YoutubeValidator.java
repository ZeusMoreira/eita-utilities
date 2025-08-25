package com.eitasutilities.cs2.validator;

import com.eitasutilities.cs2.exceptions.LinkInvalidoException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class YoutubeValidator {

    @Value("${youtube.api-key}")
    private String youtubeApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void validarVideoExistente(String url) {
        String videoId = extrairId(url);
        if (videoId == null) {
            throw new LinkInvalidoException("Não foi possível extrair um ID de vídeo do link.");
        }

        if(!ehVideoValido(url)) {
            throw new LinkInvalidoException("O vídeo informado não existe ou não está disponível.");
        }
    }

    private String extrairId(String url) {
        var p1 = java.util.regex.Pattern.compile("[?&]v=([a-zA-Z0-9_-]{11})");
        var p2 = java.util.regex.Pattern.compile("youtu\\.be/([a-zA-Z0-9_-]{11})");
        var p3 = java.util.regex.Pattern.compile("youtube\\.com/shorts/([a-zA-Z0-9_-]{11})");

        var m1 = p1.matcher(url); if (m1.find()) return m1.group(1);
        var m2 = p2.matcher(url); if (m2.find()) return m2.group(1);
        var m3 = p3.matcher(url); if (m3.find()) return m3.group(1);
        return null;
    }

    public boolean ehVideoValido(String url) {
        String videoId = extrairId(url);
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
