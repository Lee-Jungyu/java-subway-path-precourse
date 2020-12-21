package subway;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.domain.*;
import subway.utils.IOHandler;
import subway.utils.Validator;

import java.util.List;

public class SubwayApplication {

    private IOHandler ioHandler;

    public SubwayApplication() {
        this.ioHandler = new IOHandler();
    }

    public void startApplication() {
        setInitialize();
        String selectedMenu = "0";
        while(!selectedMenu.equals("Q")) {
            selectedMenu = ioHandler.printMainMenu();

            if(selectedMenu.equals("1"))
                doStationManaging();
            if(selectedMenu.equals("2"))
                doLineManaging();
            if(selectedMenu.equals("3"))
                doSectionManaging();
            if(selectedMenu.equals("4"))
                printSubwayMap();

        }
    }

    public DijkstraShortestPath getDijkstraShortestPathByDist() {
        WeightedMultigraph<String, DefaultWeightedEdge> graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);

        for (Station s : StationRepository.stations()) {
            graph.addVertex(s.getName());
        }

        SectionRepository.sortSections();
        for (int i = 0; i < SectionRepository.sections().size(); i++) {
            int dist = SectionRepository.sections().get(i).getNextStationDist();

            if (SectionRepository.sections().get(i).getNextStationDist() == -1) continue;

            String stationName1 = SectionRepository.sections().get(i).getStationName();
            String stationName2 = SectionRepository.sections().get(i + 1).getStationName();

            graph.setEdgeWeight(graph.addEdge(stationName1, stationName2), dist);
        }

        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(graph);

        return dijkstraShortestPath;
    }

    public void printMinimumPathByDist() {

        try {
            String sourceStation = IOHandler.getString("출발역을 입력하세요.");
            if (!Validator.checkUsingStationName(sourceStation)) throw new IllegalArgumentException("존재하지 않는 역입니다.");

            String destStation = IOHandler.getString("도착역을 입력하세요.");
            if (!Validator.checkUsingStationName(destStation)) throw new IllegalArgumentException("존재하지 않는 역입니다.");

            if (sourceStation.equals(destStation)) throw new IllegalArgumentException("출발역과 도착역이 동일합니다.");

            DijkstraShortestPath dijkstraShortestPathByDist = getDijkstraShortestPathByDist();
            List<String> shortestPath = dijkstraShortestPathByDist.getPath(sourceStation, destStation).getVertexList();

            int distance = 0;
            int time = 0;

            for(int i = 0; i < shortestPath.size() - 1; i++) {
                Section section = SectionRepository.getSection(shortestPath.get(i), shortestPath.get(i + 1));
                distance += section.getNextStationDist();
                time += section.getNextStationTime();
            }

            IOHandler.printString("조회 결과");
            IOHandler.printInfo("---");
            IOHandler.printInfo("총 거리: " + distance + "km");
            IOHandler.printInfo("총 소요 시간: " + time + "분");
            IOHandler.printInfo("---");
            for(String stationName : shortestPath) {
                IOHandler.printInfo(stationName);
            }

        } catch (IllegalArgumentException e) {
            IOHandler.printError(e.getMessage());
        }
    }

    public void doStationManaging() {
        String selectedMenu = ioHandler.printStationMenu();

        if(selectedMenu.equals("1"))
            insertStation();
        if(selectedMenu.equals("2"))
            deleteStation();
        if(selectedMenu.equals("3"))
            printStations();
    }

    public void doLineManaging() {
        String selectedMenu = ioHandler.printLineMenu();

        if(selectedMenu.equals("1"))
            insertLine();
        if(selectedMenu.equals("2"))
            deleteLine();
        if(selectedMenu.equals("3"))
            printLines();
    }

    public void doSectionManaging() {
        String selectedMenu = ioHandler.printSectionMenu();

        if(selectedMenu.equals("1"))
            insertSection();
        if(selectedMenu.equals("2"))
            deleteSection();
    }

    private void insertStation() {
        try {
            String stationName = IOHandler.getString("등록할 역 이름을 입력하세요");
            if (Validator.checkUsingStationName(stationName)) throw new IllegalArgumentException("이미 등록된 역 이름입니다.");
            if (!Validator.checkNameLength(stationName, 2)) throw new IllegalArgumentException("역 이름은 2글자 이상입니다.");

            StationRepository.addStation(new Station(stationName));
            IOHandler.printInfo("지하철 역이 등록되었습니다.\n");
        } catch(IllegalArgumentException e) {
            ioHandler.printError(e.getMessage());
        }
    }

    private void deleteStation() {
        try {
            String stationName = IOHandler.getString("삭제할 역 이름을 입력하세요.");
            if (!Validator.checkUsingStationName(stationName)) throw new IllegalArgumentException("등록되지 않은 역입니다.");
            if (Validator.checkStationInLine(stationName)) throw new IllegalArgumentException("노선에 등록된 역은 삭제할 수 없습니다.");

            StationRepository.deleteStation(stationName);
            IOHandler.printInfo("지하철 역이 삭제되었습니다.");
        } catch(IllegalArgumentException e) {
            ioHandler.printError(e.getMessage());
        }
    }

    private void printStations() {
        IOHandler.printString("역 목록");

        for(Station station : StationRepository.stations())
            IOHandler.printInfo(station.getName());

        System.out.println();
    }

    private void insertLine() {
        try {
            String lineName = IOHandler.getString("등록할 노선 이름을 입력하세요.");
            if (Validator.checkUsingLineName(lineName)) throw new IllegalArgumentException("이미 등록된 노선입니다.");
            if (!Validator.checkNameLength(lineName, 2)) throw new IllegalArgumentException("노선 이름은 2글자 이상입니다.");

            String firstStationName = IOHandler.getString("등록할 노선의 상행 종점역 이름을 입력하세요.");
            if (!Validator.checkUsingStationName(firstStationName)) throw new IllegalArgumentException("존재하지 않는 역입니다.");

            String lastStationName = IOHandler.getString("등록할 노선의 하행 종점역 이름을 입력하세요.");
            if (!Validator.checkUsingStationName(lastStationName)) throw new IllegalArgumentException("존재하지 않는 역입니다.");

            Line line = new Line(lineName);
            LineRepository.addLine(line);

            Station firstStation = StationRepository.getStationByName(firstStationName);
            Station lastStation = StationRepository.getStationByName(lastStationName);

            Section section1 = new Section(firstStation, line, 1);
            Section section2 = new Section(lastStation, line, 2);

            String dist = IOHandler.getString("두 역사이의 거리를 입력하세요.");
            if (!Validator.checkIntegerType(dist)) throw new IllegalArgumentException("거리는 숫자만 입력 가능합니다.");

            String time = IOHandler.getString("두 역사이의 소요 시간을 입력하세요.");
            if (!Validator.checkIntegerType(time)) throw new IllegalArgumentException("소요시간 숫자만 입력 가능합니다.");

            SectionRepository.addSection(section1, -1, -1, Integer.parseInt(dist), Integer.parseInt(time));
            SectionRepository.addSection(section2,Integer.parseInt(dist), Integer.parseInt(time), -1, -1);

            IOHandler.printInfo("지하철 노선이 등록되었습니다\n");

        } catch (IllegalArgumentException e) {
            IOHandler.printError(e.getMessage());
        }
    }

    private void deleteLine() {
        try {
            String lineName = IOHandler.getString("삭제할 노선 이름을 입력하세요.");
            if (!Validator.checkUsingLineName(lineName)) throw new IllegalArgumentException("존재하지 않는 노선입니다.");

            SectionRepository.deleteSectionByLineName(lineName);
            LineRepository.deleteLineByName(lineName);

            IOHandler.printInfo("지하철 노선이 삭제되었습니다.\n");
        } catch (IllegalArgumentException e) {
            IOHandler.printError(e.getMessage());
        }
    }

    private void printLines() {
        IOHandler.printString("노선 목록");

        for(Line line : LineRepository.lines())
            IOHandler.printInfo(line.getName());

        System.out.println();
    }

    private void insertSection() {
        try {
            String lineName = IOHandler.getString("노선을 입력하세요.");
            if (!Validator.checkUsingLineName(lineName)) throw new IllegalArgumentException("존재하지 않는 노선입니다.");

            String stationName = IOHandler.getString("역 이을 입력하세요.");
            if (!Validator.checkUsingStationName(stationName)) throw new IllegalArgumentException("존재하지 않는 역입니다.");

            String order = IOHandler.getString("순서를 입력하세요.");
            if (!Validator.checkIntegerType(order)) throw new IllegalArgumentException("순서는 숫자만 입력 가능합니다.");

            int minNumber = 1;
            int maxNumber = LineRepository.getLineByName(lineName).getStationCount() + 1;
            if (!Validator.checkIntegerRange(Integer.parseInt(order), minNumber, maxNumber))
                throw new IllegalArgumentException(minNumber + "이상 " + maxNumber + "이하의 숫자만 가능합니다.");

            String prevStationDist = IOHandler.getString("이전 역 까지의 거리를 입력하세요.");
            if (!Validator.checkIntegerType(prevStationDist)) throw new IllegalArgumentException("거리는 숫자만 입력 가능합니다.");

            String prevStationTime = IOHandler.getString("이전 역 까지의 소요 시간을 입력하세요.");
            if (!Validator.checkIntegerType(prevStationTime)) throw new IllegalArgumentException("소요시간은 숫자만 입력 가능합니다.");

            String nextStationDist = IOHandler.getString("다음 역 까지의 거리를 입력하세요.");
            if (!Validator.checkIntegerType(nextStationDist)) throw new IllegalArgumentException("거리는 숫자만 입력 가능합니다.");

            String nextStationTime = IOHandler.getString("다음 역 까지의 소요 시간을 입력하세요.");
            if (!Validator.checkIntegerType(nextStationTime)) throw new IllegalArgumentException("소요시간은 숫자만 입력 가능합니다.");

            int pStationDist = Integer.parseInt(prevStationDist);
            int pStationTime = Integer.parseInt(prevStationTime);
            int nStationDist = Integer.parseInt(nextStationTime);
            int nStationTime = Integer.parseInt(nextStationTime);

            Station station = StationRepository.getStationByName(stationName);
            Line line = LineRepository.getLineByName(lineName);

            Section section = new Section(station, line, Integer.parseInt(order));
            SectionRepository.addSection(section, pStationDist, pStationTime, nStationDist, nStationTime);

            IOHandler.printInfo("구간이 등록되었습니다.\n");
        } catch(IllegalArgumentException e) {
            ioHandler.printError(e.getMessage());
        }
    }

    private void deleteSection() {
        try {
            String lineName = IOHandler.getString("삭제할 구간의 노선을 입력하세요.");
            if (!Validator.checkUsingLineName(lineName)) throw new IllegalArgumentException("존재하지 않는 노선입니다.");

            String stationName = IOHandler.getString("삭제할 구간의 역을 입력하세요.");
            if (!Validator.checkUsingStationName(stationName)) throw new IllegalArgumentException("존재하지 않는 역입니다.");

            if (!Validator.checkStationInLine(stationName, lineName)) throw new IllegalArgumentException(lineName + "에 " + stationName + "은 없습니다.");

            SectionRepository.deleteSection(stationName, lineName);

            IOHandler.printInfo("구간이 삭제되었습니다.\n");
        } catch (IllegalArgumentException e) {
            IOHandler.printError(e.getMessage());
        }
    }

    private void printSubwayMap() {
        IOHandler.printString(" 지하철 노선도");

        SectionRepository.sortSections();

        for(Line line : LineRepository.lines()) {
            String lineName = line.getName();

            IOHandler.printInfo(lineName);
            IOHandler.printInfo("---");

            List<Station> stations = SectionRepository.findStationsByLineName(lineName);

            for(Station station : stations)
                IOHandler.printInfo(station.getName());

            System.out.println();
        }
    }

    public void setInitialize() {
        Station station1 = new Station("교대역");
        StationRepository.addStation(station1);
        Station station2 = new Station("강남역");
        StationRepository.addStation(station2);
        Station station3 = new Station("역삼역");
        StationRepository.addStation(station3);
        Station station4 = new Station("남부터미널역");
        StationRepository.addStation(station4);
        Station station5 = new Station("양재역");
        StationRepository.addStation(station5);
        Station station6 = new Station("양재시민의숲역");
        StationRepository.addStation(station6);
        Station station7 = new Station("매봉역");
        StationRepository.addStation(station7);

        Line line1 = new Line("2호선");
        LineRepository.addLine(line1);
        Line line2 = new Line("3호선");
        LineRepository.addLine(line2);
        Line line3 = new Line("신분당선");
        LineRepository.addLine(line3);

        Section section1 = new Section(station1, line1, 1);
        SectionRepository.addSection(section1,  -1,  -1,  2,  3);
        Section section2 = new Section(station2, line1, 2);
        SectionRepository.addSection(section2, 2, 3, 2, 3);
        Section section3 = new Section(station3, line1, 3);
        SectionRepository.addSection(section3, 2, 3, -1, -1);
        Section section4 = new Section(station1, line2, 1);
        SectionRepository.addSection(section4, -1, -1, 3, 2);
        Section section5 = new Section(station4, line2, 2);
        SectionRepository.addSection(section5, 3, 2, 6, 5);
        Section section6 = new Section(station5, line2, 3);
        SectionRepository.addSection(section6, 6, 5, 1, 1);
        Section section7 = new Section(station7, line2, 4);
        SectionRepository.addSection(section7, 1, 1, -1, -1);
        Section section8 = new Section(station2, line3, 1);
        SectionRepository.addSection(section8, -1, -1, 2, 8);
        Section section9 = new Section(station5, line3, 2);
        SectionRepository.addSection(section9, 2, 8, 10, 3);
        Section section10 = new Section(station6, line3, 3);
        SectionRepository.addSection(section10, 10, 3, -1, -1);
    }
}