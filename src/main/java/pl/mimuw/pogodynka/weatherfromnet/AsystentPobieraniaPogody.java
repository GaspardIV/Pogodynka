package pl.mimuw.pogodynka.weatherfromnet;

import com.google.gson.JsonObject;
import pl.mimuw.pogodynka.pogoda.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static pl.mimuw.pogodynka.Konwertery.KonwerterJson.jakoJsonObject;

/**
 * Created by Kacper Konecki on 05.06.17.
 */
public class AsystentPobieraniaPogody {
    //zrodlo openweather
    private static final String URLOMW = "http://api.OPENWEATHERMAP.org/data/2.5/weather?q=Warsaw";
    //klucz pobierania
    private static final String KEYOMW = "a7a8a0193a967eb7bb40dc011a7e7eab";
    //zrodlo meteo
    private static final String URLMETEO = "http://www.meteo.waw.pl/";

    /**
     * pobiera ze zrodla openweather map dane pogodowe dla danej pogody (edytuje pogode)
     * @param pogoda pogoda ktorra ma edytowac
     * @throws BladPolaczenia w przypadku bledu polaczenia z serwerem
     */
    public static void pobierzOWM(Pogoda pogoda) throws BladPolaczenia {
        try {
            URL url = new URL(URLOMW);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("x-api-key", KEYOMW);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200 /*200 = OK*/) {
                System.out.println("[ERROR] Serwer" + URLOMW + " odrzucił połączenie kodem: " +
                        conn.getResponseCode());
                throw new BladPolaczenia(URLOMW);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String wiersz;
            StringBuilder output = new StringBuilder();

            while ((wiersz = br.readLine()) != null) {
                output.append(wiersz);
            }

            conn.disconnect();

            JsonObject jsonObject = jakoJsonObject(output.toString());

            pogoda.wyczyscDanePogodowe();
            pogoda.setKierunekWiatru(new KierunekWiatru((JsonObject) jsonObject.get("wind")));
            pogoda.setPredkoscWiatru(new PredkoscWiatru((JsonObject) jsonObject.get("wind")));
            pogoda.setWilgotnosc(new Wilgotnosc((JsonObject) jsonObject.get("main")));
            pogoda.setZachmurzenie(new Zachmurzenie((JsonObject) jsonObject.get("clouds")));
            pogoda.setCisnienie(new Cisnienie((JsonObject) jsonObject.get("main")));
            pogoda.setTemperatura(new Temperatura((JsonObject) jsonObject.get("main")));
            pogoda.ustawDatePobraniaPogody();
        } catch (Exception e) {
            System.out.println("[ERROR] Nie można połączyć się z serwerem.");
            throw new BladPolaczenia(URLOMW);
        }
    }

    /**
     * pobiera ze zrodla meteo map dane pogodowe dla danej pogody (edytuje pogode)
     * @param pogoda pogoda ktorra ma edytowac
     * @throws BladPolaczenia w przypadku bledu polaczenia z serwerem
     */
    public static void pobierzMeteo(Pogoda pogoda) throws BladPolaczenia {
        try {
            URL url = new URL(URLMETEO);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200 /*200 = OK*/) {
                System.out.println("[ERROR] Serwer" + URLMETEO + " odrzucił połączenie kodem: " + conn.getResponseCode());
                throw new BladPolaczenia(URLMETEO);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String wiersz;
            pogoda.wyczyscDanePogodowe();
            while ((wiersz = br.readLine()) != null) {
                if (wiersz.indexOf(Temperatura.HTMLTAG) != -1) {
                    pogoda.setTemperatura(new Temperatura(wiersz));
                } else if (wiersz.indexOf(Wilgotnosc.HTMLTAG) != -1) {
                    pogoda.setWilgotnosc(new Wilgotnosc(wiersz));
                } else if (wiersz.indexOf(Cisnienie.HTMLTAG) != -1) {
                    pogoda.setCisnienie(new Cisnienie(wiersz));
                } else if (wiersz.indexOf(PredkoscWiatru.HTMLTAG) != -1) {
                    pogoda.setPredkoscWiatru(new PredkoscWiatru(wiersz));
                } else if (wiersz.indexOf(KierunekWiatru.HTMLTAG) != -1) {
                    pogoda.setKierunekWiatru(new KierunekWiatru(wiersz));
                }
            }
            pogoda.ustawDatePobraniaPogody();
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("[ERROR] Nie można połączyć się z serwerem.");
            throw new BladPolaczenia(URLMETEO);
        }
    }
}