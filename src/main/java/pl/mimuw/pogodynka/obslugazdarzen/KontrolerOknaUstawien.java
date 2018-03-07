package pl.mimuw.pogodynka.obslugazdarzen;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pl.mimuw.pogodynka.Aplikacja;
import pl.mimuw.pogodynka.Konwertery.KonwerterKolorow;
import pl.mimuw.pogodynka.Ustawienia.Ustawienia;
import pl.mimuw.pogodynka.weatherfromnet.ZrodloPogodyEnum;

import java.util.Locale;
import java.util.Properties;

/**
 * Created by Kacper Konecki on 09.06.17.
 */
public class KontrolerOknaUstawien {
    /** ComboBox do wyboru zrodla pogody*/
    @FXML public ComboBox comboBoxZrodloPogody;
    /** Slider do wyboru czestotliwosci*/
    @FXML public Slider sliderOdswiezania;
    /** Label pokazujacy wartosc slidera */
    @FXML public Label odswiezanieWartosc;
    /** Kolor picker do wyboru koloru skorki */
    @FXML public ColorPicker wybieraczKoloru;

    /** Aplikacja bedaca aplikacja dla ktorej dziala dany Kontroler */
    private Aplikacja aplikacja;

    /** Okno ustawien aplikacji. */
    private Stage oknoUstawien;
    /** glowne okno aplikacji. */
    private Stage oknoGlowne;

    /**
     * setter okna ustawien
     * @param oknoUstawien okno ustawien
     */
    public void setOknoUstawien(Stage oknoUstawien) {
        this.oknoUstawien = oknoUstawien;
    }

    /**
     * setter glownego okna
     * @param oknoGlowne glowne okno
     */
    public void setOknoGlowne(Stage oknoGlowne) {
        this.oknoGlowne = oknoGlowne;
    }

    /**
     * setter aplikacji
     * @param aplikacja aplikacja
     */
    public void setAplikacja(Aplikacja aplikacja) {
        this.aplikacja = aplikacja;
    }


    /**
     * Funkcja wywolywana przez przycisk zatwierdz.
     * Zapisuje aktualne ustawienia do pliku i wywoluje odswiezanie
     * z nowym parametrem i ukrywa okno Ustawien.
     * @param actionEvent event ktory wywolal dana funckje
     */
    public void zatwierdz(ActionEvent actionEvent) {
        Properties aktustawienia = new Properties();
        aktustawienia.setProperty(Ustawienia.KLUCZ_ZRPOGODY,
                comboBoxZrodloPogody.getValue().toString());
        aktustawienia.setProperty(Ustawienia.KLUCZ_KOLSKORKI,
                wybieraczKoloru.getValue().toString());
        aktustawienia.setProperty(Ustawienia.KLUCZ_ODSW,
                odswiezanieWartosc.getText());
        aplikacja.getUstawienia().zapiszUstawienia(aktustawienia);
        aplikacja.zakonczAutoOddswiezanie();
        aplikacja.zacznijAutoOddswiezanie();
        aplikacja.ukryjOknoUstawien();
    }

    /**
     * funkcja wywolywana przez przycisk anuluj.
     * Wczytuje ustawienia z pliku i ustawia combobox,
     * wybieraczKOloru i sliderOdswiezania na odpowiednie wartosci.
     * Przywraca też kolor okienek na poprzzedni.
     * @param actionEvent event ktory wywolal dana funckje
     */
    public void anulujZmiany(ActionEvent actionEvent) {
        Properties ustawienia = aplikacja.getUstawienia().wczytajUstawienia();

        aplikacja.ukryjOknoUstawien();

        comboBoxZrodloPogody.setValue(ustawienia.getProperty(Ustawienia.KLUCZ_ZRPOGODY));
        wybieraczKoloru.setValue(Color.valueOf(ustawienia.getProperty(Ustawienia.KLUCZ_KOLSKORKI)));
        sliderOdswiezania.setValue(Double.valueOf(ustawienia.getProperty(Ustawienia.KLUCZ_ODSW)));

        Color aktKolor = wybieraczKoloru.getValue();

        oknoUstawien.getScene().getRoot().setStyle(
                "-fx-background-color: " + KonwerterKolorow.rgbaString(aktKolor)
        );
        oknoGlowne.getScene().getRoot().setStyle(
                "-fx-background-color: " + KonwerterKolorow.rgbaString(aktKolor)
        );

    }

    /**
     * funkcja inicjalizująca kontroler Okna ustawien.
     * Ustawia combobox, wybieraczkolorow, sliderodswiezania na wartosci
     * z pliku ustawien aplikacji. Oprocz tego dodaje listenery do slidera
     * ktory zmienia wartosc Labela odswiezaniewartosc. Ustawia kolory okien wedlug ustaiwen.
     * Oprocz tego też inicjalizuje zamkniecie okna ustawien, tak by wywolywalo
     * funkcje anulujZmiany.
     * wywolanie funkcji inicjalizuj przed wyswietleniem jakiegokolwiek okna
     * jest wymagane do poprawnego dzialania.
     */
    public void inicjalizuj() {
        Properties ustawienia = aplikacja.getUstawienia().wczytajUstawienia();
        comboBoxZrodloPogody.getItems().setAll(ZrodloPogodyEnum.values());

        comboBoxZrodloPogody.setValue(ustawienia.getProperty(Ustawienia.KLUCZ_ZRPOGODY));
        wybieraczKoloru.setValue(Color.valueOf(ustawienia.getProperty(Ustawienia.KLUCZ_KOLSKORKI)));
        String czestoscOdswiezania = ustawienia.getProperty(Ustawienia.KLUCZ_ODSW);
        sliderOdswiezania.setValue(Double.valueOf(czestoscOdswiezania));
        odswiezanieWartosc.setText(ustawienia.getProperty(Ustawienia.KLUCZ_ODSW));

        sliderOdswiezania.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                odswiezanieWartosc.setText(String.format(Locale.US, "%.01f", newValue));
            }
        });

        Color aktKolor = Color.valueOf(ustawienia.getProperty(Ustawienia.KLUCZ_KOLSKORKI));
        oknoUstawien.getScene().getRoot().setStyle(
                "-fx-background-color: " + KonwerterKolorow.rgbaString(aktKolor)
        );

        oknoGlowne.getScene().getRoot().setStyle(
                "-fx-background-color: " + KonwerterKolorow.rgbaString(aktKolor)
        );
        inicjalizujZakmniecie();
    }

    /**
     * Powoduje ze zamkniecie okna Ustawien anuluje wszystkie zmiany.
     */
    private void inicjalizujZakmniecie() {
        oknoUstawien.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                anulujZmiany(new ActionEvent());
            }
        });

    }

    /**
     * listener do eventu zmiany na wybieraczu kolorow. Aplikuje daną skórke natychmiastowo.
     * @param actionEvent event ktory wywolal dana funckje
     */
    public void pokazKolor(ActionEvent actionEvent) {
        Color aktKolor = wybieraczKoloru.getValue();
        oknoUstawien.getScene().getRoot().setStyle(
                "-fx-background-color: " + KonwerterKolorow.rgbaString(aktKolor)
        );

        oknoGlowne.getScene().getRoot().setStyle(
                "-fx-background-color: " + KonwerterKolorow.rgbaString(aktKolor)
        );
    }
}
