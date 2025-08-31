package com.eitasutilities.cs2.utils;

import com.eitasutilities.cs2.exceptions.LinkInvalidoException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class YouTubeUtils {

    private YouTubeUtils() {}

    private static final Pattern VIDEO_PATTERN_1 = Pattern.compile("[?&]v=([a-zA-Z0-9_-]{11})");
    private static final Pattern VIDEO_PATTERN_2 = Pattern.compile("youtu\\.be/([a-zA-Z0-9_-]{11})");
    private static final Pattern VIDEO_PATTERN_3 = Pattern.compile("youtube\\.com/shorts/([a-zA-Z0-9_-]{11})");

    public static String normalizarLink(String url) {
        URI uri = parseUri(url);
        String host = uri.getHost();
        String path = uri.getPath();

        if (host != null && host.contains("youtu.be")) {
            return formatarLink(uri.getPath().substring(1));
        }

        if (path != null) {
            if (path.startsWith("/shorts/")) return formatarLink(path.substring("/shorts/".length()));
            if (path.startsWith("/embed/"))  return formatarLink(path.substring("/embed/".length()));
            if (path.startsWith("/playlist/")) {
                throw new LinkInvalidoException("Links de playlist não são permitidos. Insira apenas vídeos ou shorts.");
            }
        }

        String query = uri.getQuery();
        if (query != null) {
            for (String param : query.split("&")) {
                if (param.startsWith("v=")) return formatarLink(param.substring(2));
            }
        }

        throw new LinkInvalidoException("Não foi possível extrair um ID de vídeo do link.");
    }

    public static void validarHostYoutube(String url) {
        if (url == null || url.isBlank()) {
            throw new LinkInvalidoException("O campo referente ao link deve ser preenchido!");
        }

        URI uri = parseUri(url);
        String host = uri.getHost();
        if (host == null ||
                !(host.equalsIgnoreCase("youtube.com") || host.equalsIgnoreCase("www.youtube.com") || host.equalsIgnoreCase("youtu.be"))) {
            throw new LinkInvalidoException("O link informado não pertence ao YouTube.");
        }
    }

    public static String extrairId(String url) {
        return extrairPrimeiroMatch(url, VIDEO_PATTERN_1, VIDEO_PATTERN_2, VIDEO_PATTERN_3);
    }

    private static URI parseUri(String url) {
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new LinkInvalidoException("O link informado não é uma URL válida.");
        }
    }

    private static String formatarLink(String videoId) {
        return "https://www.youtube.com/watch?v=" + videoId;
    }

    private static String extrairPrimeiroMatch(String url, Pattern... patterns) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) return matcher.group(1);
        }
        return null;
    }
}
