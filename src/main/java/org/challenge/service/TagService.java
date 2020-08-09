package org.challenge.service;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.collection.CompositeCollection;
import org.apache.commons.lang3.StringUtils;
import org.challenge.utils.AutotagChallenteUtils;
import org.challenge.utils.StopWords;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service that is responsible to get text data and return tags.
 *
 * @author alen bakovic (alen@getconvey.com)
 */
public class TagService {

    private static Logger log = LoggerFactory.getLogger(TagService.class);
    private static String ENGLISH_TOKEN = "src/main/resources/en-token.bin";
    // I copied this class from net
    private static StopWords STOP_WORDS = StopWords.getInstance();


    public List<String> getTags(String url) throws IOException {
        final List<String> tokens = getText(url).map(this::tokenizeText).orElse(Collections.emptyList());
        final Map<String, Integer> wordCount = countWords(tokens);
        final Map<String, Integer> sortedKeywords = AutotagChallenteUtils.sortByValue(wordCount);
        return fetchTags(sortedKeywords);
    }

    private List<String> fetchTags(Map<String, Integer> sortedKeywords) {
        final List<String> tags = sortedKeywords.entrySet().stream()
                .filter(map -> map.getValue() > 2)
                .map(Map.Entry::getKey)
                .limit(10)
                .collect(Collectors.toList());

        // return first keyword if there is no words repeated more than 2 times
        if (CollectionUtils.isEmpty(tags)) {
            return sortedKeywords.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
        }
        return tags;
    }

    private Optional<String> getText(String url) throws IOException {
        try {
            return Optional.ofNullable(Jsoup.connect(url).get()
                    .select("p")
                    .text());
        } catch (IOException exception) {
            log.error("Could not connect to provided URL: {}", url);
            log.error(exception.getMessage());
            throw exception;
        }
    }

    private List<String> tokenizeText(String text) {
        try (InputStream modelIn = new FileInputStream(ENGLISH_TOKEN)) {
            final TokenizerModel tokenModel = new TokenizerModel(modelIn);
            final TokenizerME tokenizer = new TokenizerME(tokenModel);
            return Arrays.stream(tokenizer.tokenize(text))
                    .map(StringUtils::lowerCase)
                    .filter(StringUtils::isAlphanumeric) // this should remove punctuation
                    .filter(STOP_WORDS::isNotStopWord) // remove stop and short words
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            log.error("Could find resource file: {}", ENGLISH_TOKEN);
        } catch (IOException e) {
            log.error("Error while tokenizing text", e);
        }
        return Collections.emptyList();
    }

    public Map<String, Integer> countWords(List<String> tokens) {
        final Map<String, Integer> wordCount = new HashMap<>();
        tokens.stream().forEach(token -> {
            if (wordCount.containsKey(token)) {
                wordCount.put(token, wordCount.get(token) + 1);
            } else {
                wordCount.put(token, Integer.valueOf(1));
            }
        });
        return wordCount;
    }


}
