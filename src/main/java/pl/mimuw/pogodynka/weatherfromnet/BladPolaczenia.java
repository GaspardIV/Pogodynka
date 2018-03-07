package pl.mimuw.pogodynka.weatherfromnet;

/**
 * Created by Kacper Konecki on 10.06.17.
 */
public class BladPolaczenia extends Exception {
    //url dla ktorego nie udalo sie polaczyc
    private String url;

    public BladPolaczenia(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
