package org.example.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StatisticDataService {
    public Map<StringBuilder, Double> getStatisticData(String filePath) {
        Map<StringBuilder, Double> map = new HashMap<>();
        List<Path> files = getFiles(filePath);

        for (Path path : files) {
            try (FileInputStream fileInputStream = new FileInputStream(path.toString());
                 Scanner scanner = new Scanner(fileInputStream,
                         StandardCharsets.UTF_8).useDelimiter("</violation>")){
                while (scanner.hasNext()) {
                    String xmlLine = scanner.next();
                    if (!xmlLine.trim().equals("</violations>")) {
                        map.put(new StringBuilder(getValueFromTag(xmlLine, xmlLine.indexOf("<type>"))),
                                Double.valueOf(getValueFromTag(xmlLine, xmlLine.indexOf("<fine_amount>"))));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Can`t read data from file", e);
            }
        }
        return map;
    }

    private List<Path> getFiles(String path) {
        try {
            return Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Can`t get files");
        }
    }

    private String getValueFromTag(String line, int index) {
        return line.substring(line.indexOf(">", index) + 1,
                line.indexOf("<", index + 1));
    }
}
