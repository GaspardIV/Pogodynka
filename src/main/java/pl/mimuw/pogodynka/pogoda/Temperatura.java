package pl.mimuw.pogodynka.pogoda;

import com.google.gson.JsonObject;

/**
 * Created by Kacper Konecki on 04.06.17.
 */
public class Temperatura extends DanaPogodowa {
    /** jsonowy tag (wiecej o nim w konstruktorze DanejPogowej) */
    private static final String JSONTAG = "temp";
    /** htmlowy tag (wiecej o nim w konstruktorze DanejPogowej) */
    public static final String HTMLTAG = "id=\"PARAM_TA\">";

    /** Konstruktor wywolujacty konstruktor znadklasy dla danego tagu  i tranformujacy na stopnie celcjusza*/
    public Temperatura(JsonObject jso) {
        super(jso, JSONTAG);
        if (wartosc != null) {
            wartosc = wartosc - 273.15; // Chce trzymac wartosc nie w Kelvinach tylko w C
        }
    }
    /** Konstruktor wywolujacty konstruktor znadklasy*/
    public Temperatura(Double wartosc) {
        super(wartosc);
    }

    /** Konstruktor wywolujacty konstruktor znad klasy dla danego tagu */
    public Temperatura(String htmlWiersz) {
        super(htmlWiersz, HTMLTAG);

    }
}
