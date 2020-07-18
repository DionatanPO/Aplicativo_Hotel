package com.example.app.model;

public final class Url {
    static String url = "http://192.168.0.4:8080";

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        Url.url = url;
    }
}
