package subway.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StationRepository {
    private static final List<Station> stations = new ArrayList<>();

    public static List<Station> stations() {
        return Collections.unmodifiableList(stations);
    }

    public static void addStation(Station station) {
        stations.add(station);
    }

    public static boolean deleteStation(String name) {
        return stations.removeIf(station -> Objects.equals(station.getName(), name));
    }

    public static int findStation(String name) {
        for(int i = 0; i < stations.size(); i++) {
            if(stations.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }

    public static Station getStationByName(String stationName) {
        int idx = findStation(stationName);
        if(idx == -1) return null;
        return stations.get(idx);
    }
}
