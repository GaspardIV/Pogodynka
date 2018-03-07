package pl.mimuw.pogodynka.pogoda;

import com.google.gson.JsonObject;

/**
 * Created by Kacper Konecki on 04.06.17.
 */
public class Wilgotnosc extends DanaPogodowa {
    /** jsonowy tag (wiecej o nim w konstruktorze DanejPogowej) */
    private static final String JSONTAG= "humidity";
    /** htmlowy tag (wiecej o nim w konstruktorze DanejPogowej) */
    public static final String HTMLTAG = "id=\"PARAM_RH\">";
    /** Konstruktor wywolujacty konstruktor znadklasy dla odpowiedniego tagu*/
    public Wilgotnosc(JsonObject jso) {
        super(jso, JSONTAG);
    }
    /** Konstruktor wywolujacty konstruktor z nadklasy*/
    public Wilgotnosc(Double wartosc) {
        super(wartosc);
    }
    /** Konstruktor wywolujacty konstruktor znadklasy dla odpowiedniego tagu*/
    public Wilgotnosc(String htmlWiersz) {
        super(htmlWiersz, HTMLTAG);
    }
}
