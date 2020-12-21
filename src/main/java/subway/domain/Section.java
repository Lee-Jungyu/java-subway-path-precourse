package subway.domain;

public class Section implements Comparable<Section> {
    Station station;
    Line line;
    int order;

    int prevStationDist;
    int prevStationTime;
    int nextStationDist;
    int nextStationTime;

    public Section(Station station, Line line, int order) {
        this.station = station;
        this.line = line;
        this.order = order;

        this.prevStationDist = -1;
        this.prevStationTime = -1;
        this.nextStationDist = -1;
        this.nextStationTime = -1;
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

    public int getPrevStationDist() { return prevStationDist; }

    public int getPrevStationTime() { return prevStationTime; }

    public int getNextStationDist() { return nextStationDist; }

    public int getNextStationTime() { return nextStationTime; }

    public void setPrevStationDistAndTime(int dist, int time) {
        this.prevStationDist = dist;
        this.prevStationTime = time;
    }

    public void setNextStationDistAndTime(int dist, int time) {
        this.nextStationDist = dist;
        this.nextStationTime = time;
    }

    @Override
    public int compareTo(Section s) {
        if(this.getLineName().equals(s.getLineName())) {
            return String.valueOf(this.getOrder()).compareTo(String.valueOf(s.getOrder()));
        }
        return this.getLineName().compareTo(s.getLineName());
    }

}
