package pl.mimuw.pogodynka.pogoda;

import com.google.gson.JsonObject;

/**
 * Created by Kacper Konecki on 04.06.17.
 */
public class Zachmurzenie extends DanaPogodowa {
    /** jsonowy tag (wiecej o nim w konstruktorze DanejPogowej) */
    private static final String JSONTAG= "all";
    /** htmlowy tag (wiecej o nim w konstruktorze DanejPogowej) */
    public static final String HTMLTAG = "stronaMeteoNieMaZachmurzenia8373719";
    /** Konstruktor wywolujacty konstruktor znadklasy dla odpowiedniego tagu*/
    public Zachmurzenie(JsonObject jso) {
        super(jso, JSONTAG);
    }
    /** Konstruktor wywolujacty konstruktor znadklasy*/
    public Zachmurzenie(Double wartosc) {
        super(wartosc);
    }
    /** Konstruktor wywolujacty konstruktor znadklasy dla odpowiedniego tagu*/
    public Zachmurzenie(String htmlWiersz) {
        super(htmlWiersz, HTMLTAG);
    }
}
