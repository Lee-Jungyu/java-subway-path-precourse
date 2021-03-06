package subway.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LineRepository {
    private static final List<Line> lines = new ArrayList<>();

    public static List<Line> lines() {
        return Collections.unmodifiableList(lines);
    }

    public static void addLine(Line line) {
        lines.add(line);
    }

    public static boolean deleteLineByName(String name) {
        return lines.removeIf(line -> Objects.equals(line.getName(), name));
    }

    public static int findLine(String lineName) {
        for(int i = 0; i < lines.size(); i++) {
            if(lines.get(i).getName().equals(lineName)) return i;
        }
        return -1;
    }

    public static Line getLineByName(String lineName) {
        int idx = findLine(lineName);
        if(idx == -1) return null;
        return lines.get(idx);
    }
}
