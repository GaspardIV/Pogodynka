package pl.mimuw.pogodynka.Konwertery;

import javafx.scene.paint.Color;

/**
 * Created by Kacper Konecki on 10.06.17.
 */
public class KonwerterKolorow {
    /**
     * zwraca Stringowa reprezentacje danego koloru zgodną z CSS.
     * (można to wkleić do pliku css i bedzie dzialac).
     * @param kolor kolor do przekonwertowania
     * @return String bedacy reprezentacją koloru w formacie rgba()
     */
    public static String rgbaString(Color kolor) {
        int red = (int) (kolor.getRed()*100);
        int green = (int) (kolor.getGreen()*100);
        int blue = (int) (kolor.getBlue()*100);
        double opacity = kolor.getOpacity();
        return "rgba("+red+"%,"+green+"%,"+blue+"%,"+opacity+")";
    }
}
