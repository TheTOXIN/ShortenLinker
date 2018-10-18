package com.toxin.shorten.util;


import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {

    private final static String API_HAHSK_LINK = "link";

    public static String getURLBase(HttpServletRequest request) {
        try {
            URL requestURL = new URL(request.getRequestURL().toString());
            String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
            return requestURL.getProtocol() + "://" + requestURL.getHost() + port + "/";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String buildHashLink(String url, String hash) {
        return url + API_HAHSK_LINK + "/" + hash;
    }

}
