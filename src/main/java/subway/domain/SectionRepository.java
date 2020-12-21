package subway.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SectionRepository {
    private static List<Section> sections = new ArrayList<>();

    public static List<Section> sections() {
        return sections;
    }

    public static void addSection(Section section) {
        for(Section s : sections) {
            if(s.getLineName().equals(section.getLineName()) && s.getOrder() >= section.getOrder()) {
                s.increaseOrder();
            }
        }
        sections.add(section);
        LineRepository.getLineByName(section.getLineName()).increaseStationCount();
    }

    public static void deleteSection(String stationName, String lineName) {
        int order = -1;
        for(int i = 0; i < sections.size(); i++) {
            Section section = sections.get(i);
            if(section.getStationName().equals(stationName) && section.getLineName().equals(lineName)) {
                order = section.getOrder();
                sections.remove(i);
            }
        }

        for(int i = 0; i < sections.size(); i++) {
            Section section = sections.get(i);
            if(section.getLineName().equals(lineName) && section.getOrder() > order) {
                section.decreaseOrder();
            }
        }

        LineRepository.getLineByName(lineName).decreaseStationCount();
    }

    public static void deleteSectionByLineName(String lineName) {
        for(Section section : sections) {
            if(section.getLineName().equals(lineName)) {
                sections.remove(section);
            }
        }
    }

    public static boolean findByStationName(String stationName) {
        for(Section section : sections) {
            if(section.getStationName().equals(stationName)) return true;
        }
        return false;
    }

    public static boolean findByStationAndLineName(String stationName, String lineName) {
        for(Section section : sections) {
            if(section.getStationName().equals(stationName) && section.getLineName().equals(lineName)) return true;
        }
        return false;
    }

    public static List<Station> findStationsByLineName(String lineName) {
        List<Station> stations = new ArrayList<>();

        for(Section section : sections) {
            if(section.getLineName().equals(lineName))
                stations.add(section.getStation());
        }

        return stations;
    }

    public static void sortSections() {
        Collections.sort(sections);
    }
}
