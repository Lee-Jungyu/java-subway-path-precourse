package subway.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import subway.domain.*;

public class ValidatorTest {

    @Test
    public void 역이름_중복확인_true() {
        String stationName = "강남역";
        Station station = new Station(stationName);
        StationRepository.addStation(station);
        boolean check = Validator.checkUsingStationName(stationName);
        Assertions.assertEquals(check, true);
    }

    @Test
    public void 역이름_중복확인_false() {
        String stationName = "강남역";
        String stationName2 = "강북역";
        Station station = new Station(stationName);
        StationRepository.addStation(station);
        boolean check = Validator.checkUsingStationName(stationName2);
        Assertions.assertEquals(check, false);
    }

    @Test
    public void 노선이름_중복확인_true() {
        String lineName = "2호선";
        Line line = new Line(lineName);
        LineRepository.addLine(line);
        boolean check = Validator.checkUsingLineName(lineName);
        Assertions.assertEquals(check, true);
    }

    @Test
    public void 노선이름_중복확인_false() {
        String lineName = "2호선";
        String lineName2 = "3호선";
        Line line = new Line(lineName);
        LineRepository.addLine(line);
        boolean check = Validator.checkUsingLineName(lineName2);
        Assertions.assertEquals(check, false);
    }

    @Test
    public void 글자수확인_true() {
        String stationName = "강남역";
        boolean check = Validator.checkNameLength(stationName, 2);
        Assertions.assertEquals(check, true);
    }

    @Test
    public void 글자수확인_false() {
        String stationName = "역";
        boolean check = Validator.checkNameLength(stationName, 2);
        Assertions.assertEquals(check, false);
    }

    @Test
    public void 노선에_역이_있는지_확인_true() {
        String stationName = "강남역";
        Station station = new Station(stationName);
        StationRepository.addStation(station);
        Line line = new Line("2호선");
        LineRepository.addLine(line);
        Section section = new Section(station, line, 1);
        SectionRepository.addSection(section);

        boolean check = Validator.checkStationInLine(stationName);
        Assertions.assertEquals(check, true);
    }

    @Test
    public void 노선에_역이_있는지_확인_false() {
        String stationName = "강남역";
        String stationName2 = "강북역";
        Station station = new Station(stationName);
        StationRepository.addStation(station);
        Line line = new Line("2호선");
        LineRepository.addLine(line);
        Section section = new Section(station, line, 1);
        SectionRepository.addSection(section);

        boolean check = Validator.checkStationInLine(stationName2);
        Assertions.assertEquals(check, false);
    }

    @Test
    public void 해당노선에_해당역이_있는지_확인_true() {
        String stationName = "강남역";
        String lineName = "2호선";
        Station station = new Station(stationName);
        StationRepository.addStation(station);
        Line line = new Line(lineName);
        LineRepository.addLine(line);
        Section section = new Section(station, line, 1);
        SectionRepository.addSection(section);

        boolean check = Validator.checkStationInLine(stationName, lineName);
        Assertions.assertEquals(check, true);
    }

    @Test
    public void 해당노선에_해당역이_있는지_확인_false() {
        String stationName = "강남역";
        String stationName2 = "강북역";
        String lineName = "2호선";
        Station station = new Station(stationName);
        StationRepository.addStation(station);
        Line line = new Line(lineName);
        LineRepository.addLine(line);
        Section section = new Section(station, line, 1);
        SectionRepository.addSection(section);

        boolean check = Validator.checkStationInLine(stationName2, lineName);
        Assertions.assertEquals(check, false);
    }

    @Test
    public void 숫자인지_확인_true() {
        String string = "123";

        boolean check = Validator.checkIntegerType(string);
        Assertions.assertEquals(check, true);
    }

    @Test
    public void 숫자인지_확인_false() {
        String string = "1a";

        boolean check = Validator.checkIntegerType(string);
        Assertions.assertEquals(check, false);
    }

    @Test
    public void 숫자범위_확인_true() {
        int num = 1;
        int min_num = 1;
        int max_num = 3;

        boolean check = Validator.checkIntegerRange(num, min_num, max_num);
        Assertions.assertEquals(check, true);
    }

    @Test
    public void 숫자범위_확인_false() {
        int num = 0;
        int min_num = 1;
        int max_num = 3;

        boolean check = Validator.checkIntegerRange(num, min_num, max_num);
        Assertions.assertEquals(check, false);
    }
}
