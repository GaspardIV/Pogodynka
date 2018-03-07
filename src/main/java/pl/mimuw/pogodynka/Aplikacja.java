package pl.mimuw.pogodynka;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pl.mimuw.pogodynka.Ustawienia.Ustawienia;
import pl.mimuw.pogodynka.obslugazdarzen.KontrolerOknaGlownego;
import pl.mimuw.pogodynka.obslugazdarzen.KontrolerOknaUstawien;
import pl.mimuw.pogodynka.pogoda.Pogoda;


public class Aplikacja extends Application {
    private static final String FXML_OKNO_GLOWNE = "/fxml/pogodynka-glowne.fxml";
    private static final String CSS_OKNO_GLOWNE = "/css/pogodynka-glowne.css";
    private static final String FXML_OKNO_USTAWIEN = "/fxml/pogodynka-ustawienia.fxml";
    private static final String CSS_OKNO_USTAWIEN = "/css/pogodynka-ustawienia.css";
    private static final String ADRES_IKONY = "/icons/hinduism.png";
    private static final String NAZWA_GLOWNEGO_OKNA = "Pogodynka KKonecki";
    private static final String NAZWA_USTAWIEN_OKNA = "Ustawienia";
    private Stage oknoUstawien;
    private Stage glowneOkno;
    private KontrolerOknaGlownego kontrolerOknaGlownego;
    private KontrolerOknaUstawien kontrolerOknaUstawien;
    //obiekt klasy ustawienia do komunikacji z plikami ustawien
    private Ustawienia ustawienia = new Ustawienia();
    //akt pogoda
    private Pogoda pogoda = new Pogoda();


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * javafx start
     * @param oknoPrzydzielonePrzezSystem okno przydzielone przez system
     * @throws Exception przy niepowodzeniu jakims
     */
    public void start(Stage oknoPrzydzielonePrzezSystem) throws Exception {
        glowneOkno = inicjalizujGlowneOkno(oknoPrzydzielonePrzezSystem);
        oknoUstawien = inicjalizujOknoUstawien();
        zacznijAutoOddswiezanie();
        glowneOkno.show();
    }

    /**
     * rozpoczzyna autoodswiezanie pogody
     */
    public void zacznijAutoOddswiezanie() {
        kontrolerOknaGlownego.zacznijAutoOdswiezanie();
    }

    /**
     * konczy autoodswiezanie pogody
     */
    public void zakonczAutoOddswiezanie() {
        kontrolerOknaGlownego.zakonczAutoOdswiezanie();
    }

    /**
     * inicjalizuje okno ustaiwen
     * @return zwraca okno ustawien
     * @throws Exception przy niepowodzeniu
     */
    private Stage inicjalizujOknoUstawien() throws Exception {
        oknoUstawien = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_OKNO_USTAWIEN));
        Parent korzen = fxmlLoader.load();
        kontrolerOknaUstawien = fxmlLoader.getController();
        kontrolerOknaUstawien.setAplikacja(this);
        kontrolerOknaUstawien.setOknoGlowne(glowneOkno);
        kontrolerOknaUstawien.setOknoUstawien(oknoUstawien);
        Scene scenaUstawien = new Scene(korzen);
        scenaUstawien.getStylesheets().add(getClass().getResource(CSS_OKNO_USTAWIEN).toExternalForm());
        oknoUstawien.setScene(scenaUstawien);
        oknoUstawien.setTitle(NAZWA_USTAWIEN_OKNA);
        oknoUstawien.initOwner(glowneOkno);
        oknoUstawien.setResizable(false);
        kontrolerOknaUstawien.inicjalizuj();
        return oknoUstawien;
    }

    /**
     * inicjalizuje glowne okno
     * @param oknoPrzydzielonePrzezSystem
     * @return zwraca referencje do glownego oknaa
     * @throws Exception przy niepowodzeniu inicjalizaccji okna
     */
    private Stage inicjalizujGlowneOkno(Stage oknoPrzydzielonePrzezSystem) throws Exception {
        glowneOkno = oknoPrzydzielonePrzezSystem;
        glowneOkno.getIcons().add(new Image(getClass().getResourceAsStream(ADRES_IKONY)));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_OKNO_GLOWNE));
        Parent korzen = fxmlLoader.load();
        kontrolerOknaGlownego = fxmlLoader.getController();
        kontrolerOknaGlownego.setAplikacja(this); //waazne mega
        glowneOkno.setTitle(NAZWA_GLOWNEGO_OKNA);
        Scene glownaScena = new Scene(korzen);
        glowneOkno.setScene(glownaScena);
        glownaScena.getStylesheets().add(getClass().getResource(CSS_OKNO_GLOWNE).toExternalForm());
        glowneOkno.setResizable(false);
        return glowneOkno;
    }

    /**
     * wyswietla okno ustawien
     */
    public void pokazOknoUstawien() {
        oknoUstawien.show();
    }

    /**
     * ukrywa okno ustawien
     */
    public void ukryjOknoUstawien() {
        oknoUstawien.close();
    }

    /**
     * getter
     * @return ustawienia
     */
    public Ustawienia getUstawienia() {
        return ustawienia;
    }

    /**
     * getter
     * @return pogoda
     */
    public Pogoda getPogoda() {
        return pogoda;
    }
}
