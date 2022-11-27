package org.example.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.simple.JSONValue;

public class StatisticService {
    public void getStatistic(Map<StringBuilder, Double> map, String path) {
        Map<String, Double> groupedMap = map.entrySet().stream()
                .collect(Collectors.groupingBy(
                        e -> e.getKey().toString(),
                        Collectors.averagingDouble(Map.Entry::getValue)
                ));
        try(FileWriter fileWriter = new FileWriter(path, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);) {
            bufferedWriter.write(JSONValue.toJSONString(sortMap(groupedMap)));
        } catch (IOException e) {
            throw new RuntimeException("Can`t open file");
        }
    }

    private Map<String, Double> sortMap(Map<String, Double> map) {
        Map<String, Double> resultMap = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(e -> resultMap.put(e.getKey(), e.getValue()));
        return resultMap;
    }
}
