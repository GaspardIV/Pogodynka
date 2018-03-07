package pl.mimuw.pogodynka.pogoda;

import com.google.gson.JsonArray;

/**
 * Created by Kacper Konecki on 05.06.17.
 */
public class PoziomPylkow extends DanaPogodowa {
    /** jsonowy tag (wiecej o nim w konstruktorze DanejPogowej) */
    private static final String JSONTAG= "value";

    /**
     * konstruktor polegaajacy na wyciagnieciu z Obiektu
     * JsonArray danej wartosci danej danej pogodowej na podstawie
     * Stringa bedacego tagiem wyszukiwania pola w JsonArray.
     * @param jsoTab JsonArray w ktorym wyszukuje DanaPogodowa swojej wartosci
     */
    public PoziomPylkow(JsonArray jsoTab) {
        super(null);
        boolean foundValue = false;
        if (jsoTab != null && !jsoTab.isJsonNull()) {
            for (int i = 0; i < jsoTab.size() && !foundValue; i++) {
                if (jsoTab.get(i).getAsJsonObject().get(JSONTAG) != null
                        && !jsoTab.get(i).getAsJsonObject().get(JSONTAG).isJsonNull()) {
                    Double value = jsoTab.get(i).getAsJsonObject().get(JSONTAG).getAsDouble();
                    wartosc = value;
                    foundValue = true;
                }
            }
        }
    }
    /** Konstruktor wywolujacty konstruktor znadklasy */
    public PoziomPylkow(Double wartosc) {
        super(wartosc);
    }
}
