package pl.mimuw.pogodynka.weatherfromnet;

/**
 * Created by Kacper Konecki on 06.06.17.
 */

/**
 * typ enumm dla zrodel pogody
 */
public enum ZrodloPogodyEnum {
    //http://openweathermap.org/-
    OPENWEATHERMAP {
        @Override
        public String toString() {
            return "OpenWeatherMap.org";
        }
    },
    //http://www.meteo.waw.pl/
    METEO {
        @Override
        public String toString() {
            return "LAB-EL www.meteo.waw.pl";
        }
    };

    public static ZrodloPogodyEnum getEnum(String value) {
        if (value.equals(METEO.toString())) {
            return ZrodloPogodyEnum.METEO;
        } else if (value.equals(OPENWEATHERMAP.toString())) {
            return ZrodloPogodyEnum.OPENWEATHERMAP;
        }
        throw new IllegalArgumentException();
    }
}
