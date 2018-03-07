package pl.mimuw.pogodynka.Ustawienia;

import pl.mimuw.pogodynka.weatherfromnet.ZrodloPogodyEnum;

import java.io.*;
import java.util.Properties;

/**
 * Created by Kacper Konecki on 08.06.17.
 */
public class Ustawienia {
    //sciezka pliku ustawien
    private static final String SCIEZKA_USTAWIEN = "ustawienia.properties";
    //domyslna wartosc odswiezania
    private static final int DOMYSLNE_AUTO_ODSWIEZANIE = 60;

    //klucze zapisu danych ustaiwen w pliku
    public static final String KLUCZ_ZRPOGODY = "ZRPOGODY";
    public static final String KLUCZ_KOLSKORKI = "KOLSKORKI";
    public static final String KLUCZ_ODSW = "CZSTODSW";

    private static final String[][] DOMYSLNE_USTAWIENIA = {
            {KLUCZ_ZRPOGODY, ZrodloPogodyEnum.OPENWEATHERMAP.toString()},
            {KLUCZ_KOLSKORKI, "ccffff"},
            {KLUCZ_ODSW, String.valueOf(DOMYSLNE_AUTO_ODSWIEZANIE)
            }
    };

    /**
     * zwraca domyslne ustawienia funkcja wywolywana, gdy jakis blad, lub gdy nie ma pliku z ustwieniami
     * @return ddomyslne ustawienia
     */
    public static Properties ustawieniaDomyslne() {
        Properties ustawienia = new Properties();
        for (int i = 0; i < DOMYSLNE_USTAWIENIA.length; i++) {
            ustawienia.setProperty(DOMYSLNE_USTAWIENIA[i][0], DOMYSLNE_USTAWIENIA[i][1]);
        }
        return ustawienia;
    }


    /**
     * zapisuje ustawienia do pliku
     * @param aktualneUstawienia ustaiwenia do zapisania
     */
    public void zapiszUstawienia(Properties aktualneUstawienia) {
        OutputStream output = null;
        try {
            output = new FileOutputStream(SCIEZKA_USTAWIEN);
            aktualneUstawienia.store(output, null);

        } catch (IOException io) {
            System.out.println("[ERROR] Błąd krytyczny: nie można utworzyć pliku ustawień.");
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    System.out.println("[ERROR] Błąd krytyczny: nie można zamknąć utworzonego pliku ustawień. ? ?Został on usunięty? ?");
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * wczytuje z pliku ustawien ustawienia.
     * @return ustawienia wczytane
     */
    public Properties wczytajUstawienia() {
        Properties prop = new Properties();
        InputStream input = null;
        File f = new File(SCIEZKA_USTAWIEN);

        if (!f.exists()) {
            System.out.println("[INFO] Brak pliku z ustawieniami -> generowanie pliku z ustawieniami domyślnymi");
            zapiszUstawienia(ustawieniaDomyslne());
        }

        try {
            input = new FileInputStream(f);
            prop.load(input);
        } catch (IOException e) {
            System.out.println("[ERROR] Błąd krytyczny: cos poszlo nie tak z plikiem ustawien -> korzystanie z ustawien domyslnych");
            e.printStackTrace();
            return ustawieniaDomyslne();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.out.println("[ERROR] Błąd krytyczny: nie można zamknąć pliku ustawień. ? ?Został on usunięty/jest uzywany przez inny program? ?");
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }
}