package pl.mimuw.pogodynka.pogoda;

import com.google.gson.JsonObject;

/**
 * Created by Kacper Konecki on 04.06.17.
 */
public class Cisnienie extends DanaPogodowa {
    /** jsonowy tag (wiecej o nim w konstruktorze DanejPogowej) */
    public static final String JSONTAG = "pressure";
    /** htmlowy tag (wiecej o nim w konstruktorze DanejPogowej) */
    public static final String HTMLTAG = "id=\"PARAM_PR\">";

    /** Konstruktor wywolujacty konstruktor znad klasy dla danego tagu */
    public Cisnienie(JsonObject jso) {
        super(jso, JSONTAG);
    }

    /** Konstruktor wywolujacty konstruktor znad klasy dla danego tagu */
    public Cisnienie(String htmlWiersz) {
        super(htmlWiersz, HTMLTAG);
    }

    public Cisnienie(Double wartosc) {
        super(wartosc);
    }

}
