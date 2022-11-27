package org.example;

import java.util.Map;
import org.example.service.AttributesService;
import org.example.service.StatisticDataService;
import org.example.service.StatisticService;

public class Main {
    private static String inputPath = "src/main/resources/inputXmlFiles/persons.xml";
    private static String inputStatisticPath = "src/main/resources/years";
    private static String outputXmlPath = "src/main/resources/outputFiles/persons.xml";
    private static String outputJsonPath = "src/main/resources/outputFiles/statistic.json";

    public static void main(String[] args) {
        AttributesService attributesService = new AttributesService();
        attributesService.editAttributes(inputPath, outputXmlPath);

        StatisticDataService statisticDataService = new StatisticDataService();
        Map<StringBuilder, Double> statisticData = statisticDataService.getStatisticData(inputStatisticPath);

        StatisticService statisticService = new StatisticService();
        statisticService.getStatistic(statisticData, outputJsonPath);
    }
}
