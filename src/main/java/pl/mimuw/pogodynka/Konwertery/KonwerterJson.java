package pl.mimuw.pogodynka.Konwertery;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by Kacper Konecki on 05.06.17.
 */
public class KonwerterJson {
    /*
    * Funkcje Pana Durnogi, zatem daję do nich jego komentarz (zmienione tylko nazwy na polskie)
    * A handful of helper methods for parsing string into JsonObject. These do
    * not do the actual heavy lifting, we delegate the job to Google's GSON
    * library and provide some wrappers here.
    */
    public static JsonObject jakoJsonObject(String s) {
        return parsuj(s).getAsJsonObject();
    }

    public static JsonArray jakoJsonArray(String s) {
        return parsuj(s).getAsJsonArray();
    }

    private static JsonElement parsuj(String s) {
        return new JsonParser().parse(s);
    }
}
