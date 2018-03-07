package pl.mimuw.pogodynka.pogoda;

import com.google.gson.JsonObject;

/**
 * Created by Kacper Konecki on 09.06.17.
 */
public class PredkoscWiatru extends DanaPogodowa {
    /** jsonowy tag (wiecej o nim w konstruktorze DanejPogowej) */
    private static final String JSONTAG= "speed";
    /** htmlowy tag (wiecej o nim w konstruktorze DanejPogowej) */
    public static final String HTMLTAG = "id=\"PARAM_WV\">";

    /** Konstruktor wywolujacty konstruktor znad klasy dla danego tagu */
    public PredkoscWiatru(JsonObject jso) {
        super(jso, JSONTAG);
    }
    /** Konstruktor wywolujacty konstruktor znadklasy*/
    public PredkoscWiatru(Double wartosc) {
        super(wartosc);
    }
    /** Konstruktor wywolujacty konstruktor znad klasy dla danego tagu */
    public PredkoscWiatru(String htmlWiersz) {
        super(htmlWiersz, HTMLTAG);
    }
}
