package pl.mimuw.pogodynka.pogoda;

import pl.mimuw.pogodynka.weatherfromnet.AsystentPobieraniaPogody;
import pl.mimuw.pogodynka.weatherfromnet.AsystentPobieraniaPylkow;
import pl.mimuw.pogodynka.weatherfromnet.BladPolaczenia;
import pl.mimuw.pogodynka.weatherfromnet.ZrodloPogodyEnum;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kacper Konecki on 05.06.17.
 */
public class Pogoda {
    Temperatura temperatura;
    Cisnienie cisnienie;
    Zachmurzenie zachmurzenie;
    KierunekWiatru kierunekWiatru;
    PredkoscWiatru predkoscWiatru;
    Wilgotnosc wilgotnosc;
    PoziomPylkow poziomPylowPM2_5;
    PoziomPylkow poziomPylowPM10;
    Date dataPobraniaPogody;
    Date dataPobraniaPylkow;

    public Pogoda() {
        wyczyscDanePogodowe();
        wyczyscDanePylkow();
        dataPobraniaPogody = null;
        dataPobraniaPylkow = null;
    }

    /**
     * ustawia na aktualna date
     */
    public void ustawDatePobraniaPogody() {
        this.dataPobraniaPogody = new Date();
    }

    /**
     * ustawia na aktualna date
     */
    public void ustawDatePobraniaPylkow() {
        this.dataPobraniaPylkow = new Date();
    }

    /*SETTERY*/
    public void setCisnienie(Cisnienie cisnienie) {
        this.cisnienie = cisnienie;
    }

    public void setZachmurzenie(Zachmurzenie zachmurzenie) {
        this.zachmurzenie = zachmurzenie;
    }

    public void setKierunekWiatru(KierunekWiatru kierunekWiatru) {
        this.kierunekWiatru = kierunekWiatru;
    }

    public void setPredkoscWiatru(PredkoscWiatru predkoscWiatru) {
        this.predkoscWiatru = predkoscWiatru;
    }

    public void setWilgotnosc(Wilgotnosc wilgotnosc) {
        this.wilgotnosc = wilgotnosc;
    }

    public void setPoziomPylowPM2_5(PoziomPylkow poziomPylowPM2_5) {
        this.poziomPylowPM2_5 = poziomPylowPM2_5;
    }

    public void setPoziomPylowPM10(PoziomPylkow poziomPylowPM10) {
        this.poziomPylowPM10 = poziomPylowPM10;
    }

    public void setTemperatura(Temperatura temperatura) {
        this.temperatura = temperatura;
    }

    /*Funkcje zwracające stringową reprezentacje danej danej pogodowej*/

    public String pokazTemperature() {
        return temperatura.stringowaReprezentacja();
    }

    public String pokazZachmurzenie() {
        return zachmurzenie.stringowaReprezentacja();
    }

    public String pokazCisnienie() {
        return cisnienie.stringowaReprezentacja();
    }

    public String pokazKierunekWiatru() {
        return kierunekWiatru.stringowaReprezentacja();
    }

    public String pokazPredkoscWiatru() {
        return predkoscWiatru.stringowaReprezentacja();
    }

    public String pokazWilgotnosc() {
        return wilgotnosc.stringowaReprezentacja();
    }

    public String pokazPM2_5() {
        return poziomPylowPM2_5.stringowaReprezentacja();
    }

    public String pokazPM10() {
        return poziomPylowPM10.stringowaReprezentacja();
    }

    public String pokazDaty() {
        SimpleDateFormat ft =
                new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        String wynik = "Pogoda akt. o ";
        if (dataPobraniaPylkow != null) {
            wynik = wynik + ft.format(dataPobraniaPogody) + ".\n";
        } else {
            wynik = wynik + " ---- .\n";
        }
        wynik = wynik + "P.pyłów akt. o ";
        if (dataPobraniaPylkow != null) {
            wynik = wynik + ft.format(dataPobraniaPylkow) + ".\n";
        } else {
            wynik = wynik + " ---- .\n";
        }
        return wynik;
    }


    /**
     * pobiera ze zrodla aktualne dane pogodowe
     * wyrzuca blad przy bledzie polaczenia
     * @param zrodloPogody zrodlo pogody enum
     * @throws BladPolaczenia dla nieudanego nawiazania polaczenia
     */
    public void pobierzInformacjeOPogode(ZrodloPogodyEnum zrodloPogody) throws BladPolaczenia {
        //w tej całej konstrukcji chodzi o to by nawqet gdy serwer pogodowwy zawiedzie probowac pobrac dane dot pylkow.
        //i nawet gdy oba zawiodą to by zwrociło "pustą" pogode

        switch (zrodloPogody) {
            case OPENWEATHERMAP:
                AsystentPobieraniaPogody.pobierzOWM(this);
                System.out.println("[INFO]Źródło danych pogodowych: " + ZrodloPogodyEnum.OPENWEATHERMAP.toString());
                break;
            case METEO:
                AsystentPobieraniaPogody.pobierzMeteo(this);
                System.out.println("[INFO]Źródło danych pogodowych: " + ZrodloPogodyEnum.METEO.toString());
                break;
        }
    }

    /**
     * pobiera ze aktualne dane pylkowe z internetu
     * wyrzuca blad przy bledzie polaczenia
     * @throws BladPolaczenia dla nieudanego nawiazania polaczenia
     */
    public void pobiezInformacjeOPylkach() throws BladPolaczenia {

        AsystentPobieraniaPylkow.pobierzPoziomyPylow(this);
        System.out.println("[INFO]Źródło poziomu PM2.5 i PM10: " +
                "powietrze.gios.gov.pl" +
                " wykorzystane fragmentami " +
                "(tylko inf dotyczace PM2.5 PM10 ze stacji 544 na ul. Marszalkowskiej 68)");
    }

    /**
     * ustawia na null wartosci wszystkich danych pogodywch
     */
    public void wyczyscDanePogodowe() {
        Double nullDouble = null;
        temperatura = new Temperatura(nullDouble);
        zachmurzenie = new Zachmurzenie(nullDouble);
        cisnienie = new Cisnienie(nullDouble);
        kierunekWiatru = new KierunekWiatru(nullDouble);
        predkoscWiatru = new PredkoscWiatru(nullDouble);
        wilgotnosc = new Wilgotnosc(nullDouble);
    }

    /**
     * ustawia na null wartosci wszystkich danych pylkowych
     */
    public void wyczyscDanePylkow() {
        Double nullDouble = null;
        poziomPylowPM2_5 = new PoziomPylkow(nullDouble);
        poziomPylowPM10 = new PoziomPylkow(nullDouble);
    }
}