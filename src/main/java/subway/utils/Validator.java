package subway.utils;

import subway.domain.LineRepository;
import subway.domain.SectionRepository;
import subway.domain.StationRepository;

import java.util.List;

public class Validator {
    public static boolean checkUsingStationName(String stationName) {
        return StationRepository.findStation(stationName) >= 0;
    }

    public static boolean checkUsingLineName(String lineName) {
        return LineRepository.findLine(lineName) >= 0;
    }

    public static boolean checkNameLength(String stationName, int length) {
        return stationName.length() >= length;
    }

    public static boolean checkStationInLine(String stationName) {
        return SectionRepository.findByStationName(stationName);
    }

    public static boolean checkStationInLine(String stationName, String lineName) {
        return SectionRepository.findByStationAndLineName(stationName, lineName);
    }

    public static boolean checkIntegerType(String order) {
        try {
            Integer.parseInt(order);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkIntegerRange(int number, int minNumber, int maxNumber) {
        if(number >= minNumber && number <= maxNumber) return true;
        return false;
    }

    public static boolean checkValidValue(String value, List<String> strings) {
        if(strings.contains(value)) return true;
        return false;
    }
}
