package pl.mimuw.pogodynka.pogoda;

import com.google.gson.JsonObject;

/**
 * Created by Kacper Konecki on 09.06.17.
 */
public class KierunekWiatru extends DanaPogodowa {
    /** htmlowy tag (wiecej o nim w konstruktorze DanejPogowej) */
    public static final String HTMLTAG = "id=\"PARAM_WD\">";
    /** jsonowy tag (wiecej o nim w konstruktorze DanejPogowej) */
    private static final String JSONTAG = "deg";

    /** Konstruktor wywolujacty konstruktor */
    public KierunekWiatru(Double wartosc) {
        super(wartosc);
    }
    /** Konstruktor wywolujacty konstruktor znad klasy dla danego tagu */
    public KierunekWiatru(JsonObject jso) {
        super(jso, JSONTAG);
    }
    /** Konstruktor wywolujacty konstruktor znad klasy dla danego tagu */
    public KierunekWiatru(String htmlWiersz) {
        super(htmlWiersz, HTMLTAG);
    }
}
