package pl.mimuw.pogodynka.obslugazdarzen;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import pl.mimuw.pogodynka.Aplikacja;
import pl.mimuw.pogodynka.Ustawienia.Ustawienia;
import pl.mimuw.pogodynka.pogoda.Pogoda;
import pl.mimuw.pogodynka.weatherfromnet.BladPolaczenia;
import pl.mimuw.pogodynka.weatherfromnet.ZrodloPogodyEnum;

import java.util.Properties;

/**
 * Created by Kacper Konecki on 07.06.17.
 */
public class KontrolerOknaGlownego /*implements Initializable*/ {
    /**
     * timeline to Obiekt klasy timeline sluzacy do autoodswiezania
     */
    private static Timeline timeline = new Timeline();
    /**
     * button błędu -> pojawia się kiedy program napotka blad pobierania.
     */
    public Button errorTrojkat;
    /**
     * label tekstu bledu -> pojawia sie po najechaniu na errorTrojkat
     */
    @FXML
    public Label errorTekst;
    /**
     * przycisk odswiezenia
     */
    @FXML
    private Button odswiez;
    /**
     * pasek na ktorym sa daty aktualizacji
     */
    @FXML
    private Label pasekDaty;
    /**
     * label z aktualna temperatura
     */
    @FXML
    private Label lblTmp;
    /**
     * label z aktualna wilgotnoscia
     */
    @FXML
    private Label lblWilg;
    /**
     * label z aktualnym cisnieniem
     */
    @FXML
    private Label lblCisn;
    /**
     * label z aktualna predkoscia wiatru
     */
    @FXML
    private Label lblVWiatru;
    /**
     * label z aktualnym kierunkiem wiatru
     */
    @FXML
    private Label lblDirWiatru;
    /**
     * label z aktualnym zachmurzeniem
     */
    @FXML
    private Label lblZach;
    /**
     * label z aktualnym poziomem pylkow 2.5
     */
    @FXML
    private Label lblPM2_5;
    /**
     * label z aktualnym poziomem pylkow 10
     */
    @FXML
    private Label lblPM10;
    /**
     * Aplikacja bedaca aplikacja dla ktorej dziala dany Kontroler
     */
    private Aplikacja aplikacja;

    /**
     * Rozpoczyna animacje odswieżania (krecacy sie przycisk odswież) -> kończy się po jednym obrocie.
     */
    private void animacjaOdswiezenia() {
        odswiez.setDisable(true);
        odswiez.setRotate(0);
        RotateTransition rt = new RotateTransition(Duration.millis(1000), odswiez);
        rt.setToAngle(360);
        rt.setCycleCount(1);
        rt.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                odswiez.setDisable(false);
            }
        });
        rt.play();
    }

    /**
     * wczytuje z pliku z ustawieniami aktualne zrodlo pobierania pogody
     *
     * @return aktualne zrodlo pobierania pogody (ENUM)
     */
    private ZrodloPogodyEnum wczytajZrodloZUst() {
        Properties ustawienia = aplikacja.getUstawienia().wczytajUstawienia();
        String zrodloStr = ustawienia.getProperty(Ustawienia.KLUCZ_ZRPOGODY);
        return ZrodloPogodyEnum.getEnum(zrodloStr);
    }

    /**
     * Dodaje do listy bledow poleczen blad polaczenia i pojawia trojkatt bledu
     *
     * @param bladPolaczenia dodawant blad
     */
    public void dodajBladPolaczenia(BladPolaczenia bladPolaczenia) {
        errorTrojkat.setVisible(true);
        errorTekst.setText("\nNie udało się połączyć z serwerem: " + bladPolaczenia.getUrl() + "\n"
                + errorTekst.getText());
    }

    /**
     * zeruje bledy polaczen i ukrywa trojkat bledu
     */
    private void wyzerujBledyPolaczenia() {
        errorTrojkat.setVisible(false);
        errorTekst.setText("\n[sprawdz swoje polaczenie z internetem]");
    }

    /**
     * odswieza pogode i wklada pobrane dane w odpowienie labele.
     * Jesli natrafi na blad polaczenia wyswietla trojkat bledu z danym bledem.
     *
     * @param actionEvent Event wywolania
     */
    @FXML
    public void odswiezPogode(ActionEvent actionEvent) {
        wyzerujBledyPolaczenia();//by nie nawarstwialy sie bledy po poprzedniej probie odswiezenia
        animacjaOdswiezenia();
        Pogoda aktPogoda = aplikacja.getPogoda();

        try {
            aktPogoda.pobierzInformacjeOPogode(wczytajZrodloZUst());
        } catch (BladPolaczenia bladPolaczenia) {
            dodajBladPolaczenia(bladPolaczenia);
        }

        try {
            aktPogoda.pobiezInformacjeOPylkach();
        } catch (BladPolaczenia bladPolaczenia) {
            dodajBladPolaczenia(bladPolaczenia);
        }

        lblTmp.setText(aktPogoda.pokazTemperature());
        lblCisn.setText(aktPogoda.pokazCisnienie());
        lblWilg.setText(aktPogoda.pokazWilgotnosc());
        lblZach.setText(aktPogoda.pokazZachmurzenie());
        lblVWiatru.setText(aktPogoda.pokazPredkoscWiatru());
        lblDirWiatru.setText(aktPogoda.pokazKierunekWiatru());
        lblPM2_5.setText(aktPogoda.pokazPM2_5());
        lblPM10.setText(aktPogoda.pokazPM10());
        pasekDaty.setText(aktPogoda.pokazDaty());
    }

    /**
     * wyswietla okno ustawien.
     *
     * @param actionEvent event wywolujacy dana funkcje
     */
    @FXML
    public void pokazOknoUstawien(ActionEvent actionEvent) {
        aplikacja.pokazOknoUstawien();
    }

    /**
     * setter aplikacji.
     *
     * @param aplikacja aplikacjsa
     */
    public void setAplikacja(Aplikacja aplikacja) {
        this.aplikacja = aplikacja;

    }

    /**
     * wyswietla tekst bledu.
     *
     * @param mouseEvent event wywolywujacy wyswietlenie
     */
    public void wyswietlBlad(MouseEvent mouseEvent) {

        errorTekst.setTextFill(Color.RED);
        errorTekst.setVisible(true);

    }

    /**
     * chowa tekst bledu
     *
     * @param mouseEvent event wywolujacy dana funkcje
     */
    public void schowajBlad(MouseEvent mouseEvent) {
        errorTekst.setVisible(false);
    }

    /**
     * rozpoczyna autoodswiezanie pogody co okreslony czas, dane co ile pobiera z pliku ustawien.
     */
    public void zacznijAutoOdswiezanie() {
        Properties ustawienia = aplikacja.getUstawienia().wczytajUstawienia();
        String coIleSekundString = ustawienia.getProperty(Ustawienia.KLUCZ_ODSW);
        double coIleSekund = Double.parseDouble(coIleSekundString);
        long coIleMilisekund = (long) coIleSekund * 1000;

        timeline.stop();
        odswiezPogode(new ActionEvent());
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(coIleMilisekund),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        odswiezPogode(new ActionEvent());
                    }
                }
        ));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * konczy autoodswiezanie,
     * Wklejam tu notatke z klasy Timeline.
     * Note: stop() is an asynchronous call, the Animation may not stop immediately.
     */
    public void zakonczAutoOdswiezanie() {
        timeline.stop();
        timeline.pause();
        timeline.setCycleCount(0);
        timeline.stop();
        timeline.play();
        timeline.stop();
    }
}
