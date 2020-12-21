package subway.domain;

public class Section implements Comparable<Section> {
    Station station;
    Line line;
    int order;

    public Section(Station station, Line line, int order) {
        this.station = station;
        this.line = line;
        this.order = order;
    }

    public Station getStation() {
        return station;
    }

    public Line getLine() {
        return line;
    }

    public String getStationName() {
        return station.getName();
    }

    public String getLineName() {
        return line.getName();
    }

    public int getOrder() {
        return order;
    }

    public void increaseOrder() {
        order++;
    }

    public void decreaseOrder() {
        order--;
    }

    @Override
    public int compareTo(Section s) {
        if(this.getLineName().equals(s.getLineName())) {
            return String.valueOf(this.getOrder()).compareTo(String.valueOf(s.getOrder()));
        }
        return this.getLineName().compareTo(s.getLineName());
    }

}
