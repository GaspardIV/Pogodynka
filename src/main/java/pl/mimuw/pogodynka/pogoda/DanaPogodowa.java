package pl.mimuw.pogodynka.pogoda;

import com.google.gson.JsonObject;

/**
 * Created by Kacper Konecki on 04.06.17.
 */
public abstract class DanaPogodowa {
    /**
     * Wartosc danej danej pogodowej.
     */
    protected Double wartosc;

    /**
     * konstruktor najzwyklejszy.
     * @param wartosc wartosc
     */
    public DanaPogodowa(Double wartosc) {
        this.wartosc = wartosc;
    }

    /**
     * konstruktor polegaajacy na wyciagnieciu z Obiektu
     * JsonObject danej wartosci danej danej pogodowej na podstawie
     * Stringa bedacego tagiem wyszukiwania pola w JsonObject.
     * @param jso JsonObject w ktorym wyszukuje DanaPogodowa swojej wartosci
     * @param JsonTag Jsonowy tag danej danej pogodowej.
     */
    public DanaPogodowa(JsonObject jso, String JsonTag) {
        if (jso.get(JsonTag) != null && !jso.get(JsonTag).isJsonNull()) {
            this.wartosc = Double.parseDouble(String.valueOf(jso.get(JsonTag)));
        } else {
            this.wartosc = null;
        }
    }

    /**
     * Konstruktor parsujacy z danego wiersza strony HTML wartosc Danej danej pogodowej.
     * Dziala na podstawie taga danej tanej pogodwej zalozenie jest takie zee
     * [TAG][WARTOSC] odrazu po tagu jest wartosc.
     * @param htmlWiersz wiersz strony html do sprasowania
     * @param HTMLTAG tag htmlowy danej danej pogodowej po ktorym wystepuje wartosc w double
     */
    public DanaPogodowa(String htmlWiersz, String HTMLTAG) {
        int pozycja = htmlWiersz.indexOf(HTMLTAG) + HTMLTAG.length();
        wartosc = wczytajDoubleZWierszaOdPozycji(htmlWiersz, pozycja);
    }

    /**
     * wczytuje double z danego wiersza od danej pozycji w danym wierszy.
     * @param wiersz z ktorego wczytuje double
     * @param pozycja od ktorej wczytywany jest double
     * @return wartosc sparsowanego doubla, lub null.
     */
    private static Double wczytajDoubleZWierszaOdPozycji(String wiersz, int pozycja) {
        String wartoscStr = "";
        try {
            while ((wiersz.charAt(pozycja) >= '0' && wiersz.charAt(pozycja) <= '9')
                    || (wiersz.charAt(pozycja) == '-')
                    || (wiersz.charAt(pozycja) == ',')) {

                if (wiersz.charAt(pozycja) == ',') {
                    wartoscStr = wartoscStr + '.';
                } else {
                    wartoscStr = wartoscStr + wiersz.charAt(pozycja);
                }
                pozycja++;
            }
            return Double.parseDouble(wartoscStr);
        } catch (Exception e) {
            System.out.println("[Warning]Błąd przy parsowaniu wiersza");
            return null;
        }

    }

    /**
     * zwraca stringowa reprezentacje Wartosci danej danej.
     * ( - - - -) w przypadku nulla
     * @return Stringowa reprezentacja
     */
    public String stringowaReprezentacja() {
        if (wartosc == null) {
            return " - - - - ";
        } else {
            return String.format("%.01f", wartosc);
        }
    }
}
