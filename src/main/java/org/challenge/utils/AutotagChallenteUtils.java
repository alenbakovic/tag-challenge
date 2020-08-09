package org.challenge.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author alen bakovic (alen@getconvey.com)
 */
public class AutotagChallenteUtils {

    public static Map<String, Integer> sortByValue(Map<String, Integer> wordCounts) {
        return wordCounts.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
