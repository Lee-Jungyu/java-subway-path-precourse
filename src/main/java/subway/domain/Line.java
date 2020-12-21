package subway.domain;

public class Line {
    private String name;
    private int stationCount = 0;

    public Line(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // 추가 기능 구현

    public int getStationCount() {
        return stationCount;
    }

    public void increaseStationCount() {
        stationCount++;
    }

    public void decreaseStationCount() {
        stationCount--;
    }
}
