package pl.mimuw.pogodynka.weatherfromnet;

import com.google.gson.JsonObject;
import pl.mimuw.pogodynka.pogoda.Pogoda;
import pl.mimuw.pogodynka.pogoda.PoziomPylkow;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static pl.mimuw.pogodynka.Konwertery.KonwerterJson.jakoJsonObject;

/**
 * Created by Kacper Konecki on 05.06.17.
 */
public class AsystentPobieraniaPylkow {

    private final static String urlPm10 = "http://api.gios.gov.pl/pjp-api/rest/data/getData/3694";
    private final static String urlPm2_5 = "http://api.gios.gov.pl/pjp-api/rest/data/getData/16287";

    /**
     * pobiera poziomy pylkow do danej w parametrach pogody
     * @param pogoda pogoda dla ktorej ma byc pobrany poziom pylkow i ustawiony w niej
     * @throws BladPolaczenia w przypadku bledu polaczenia z serwerem
     */
    public static void pobierzPoziomyPylow(Pogoda pogoda) throws BladPolaczenia {
        pogoda.setPoziomPylowPM2_5(pobierzPoziomPylowz(urlPm2_5));
        pogoda.setPoziomPylowPM10(pobierzPoziomPylowz(urlPm10));
        pogoda.ustawDatePobraniaPylkow();
    }

    /**
     * pobiera poziom pylkow z danego adresu
     * @param strUrl adres url z poziomem pylkow
     * @return PoziomPylkow pobrany
     * @throws BladPolaczenia gdy blad polaczenia z serwerem
     */
    private static PoziomPylkow pobierzPoziomPylowz(String strUrl) throws BladPolaczenia {
        PoziomPylkow poziomPylkow;

        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200 /*200 == Wszystko OK*/) {
                System.out.println("[ERROR] Serwer" + strUrl + " odrzucił połączenie kodem: " + conn.getResponseCode());
                throw new BladPolaczenia(strUrl);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String aktualnaLinia;
            StringBuilder output = new StringBuilder();

            while ((aktualnaLinia = br.readLine()) != null) {
                output.append(aktualnaLinia);
            }
            conn.disconnect();

            JsonObject jsonObject = jakoJsonObject(output.toString());
            poziomPylkow = new PoziomPylkow(jsonObject.getAsJsonArray("values"));
            return poziomPylkow;
        } catch (Exception e) {
            System.out.println("[ERROR] Nie można połączyć się z serwerem.");
            throw new BladPolaczenia(strUrl);
        }
    }
}
