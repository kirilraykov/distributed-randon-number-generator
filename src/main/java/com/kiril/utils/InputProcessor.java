package com.kiril.utils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.abs;
import static java.util.Objects.*;
import static java.util.Objects.requireNonNull;

/**
 * Utils Class for processing and validating the user input required for Random Number Generator.
 */
public class InputProcessor {
    public static void validateInputLists(List<Integer> randomNumbers, List<Double> probabilities) {
        verifyNonNullInput(randomNumbers, probabilities);
        verifyEqualListsSize(randomNumbers, probabilities);
        verifyNonNullElements(randomNumbers, probabilities);
        verifyProbabilitySum(probabilities);
        verifyProbabilityValues(probabilities);
    }

    public static Map<Integer, Double> convertInputListsToMap(List<Integer> randomNumbers, List<Double> probabilities) {
        return IntStream.range(0, randomNumbers.size())
                .boxed()
                .collect(Collectors.toMap(randomNumbers::get, probabilities::get));
    }

    public static void verifyNonNullInput(Object... objects) {
        requireNonNull(objects, "Provided input should NOT be null.");
    }

    public static <K, V> void verifyNonNullMapEntries(Map<K, V> providedMap) {
        String errorMessage = providedMap.entrySet()
                .stream()
                .filter(entry -> isNull(entry.getKey()) || isNull(entry.getValue()))
                .map(entry -> isNull(entry.getKey()) ? "key" : "value")
                .findFirst()
                .map(type -> "Provided map should NOT contain any null " + type + ".")
                .orElse(null);

        if (errorMessage != null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private static void verifyNonNullElements(List<Integer> randomNumbers, List<Double> probabilities) {
        checkForNulls(randomNumbers, "random numbers");
        checkForNulls(probabilities, "probabilities");
    }

    private static void verifyEqualListsSize(List<Integer> randomNumbers, List<Double> probabilities) {
        if (randomNumbers.size() != probabilities.size()) {
            throw new IllegalArgumentException("Provided numbers and probabilities lists should have equal size.");
        }
    }

    private static void verifyProbabilitySum(List<Double> entries) {
        double sum = entries.stream().mapToDouble(Double::doubleValue).sum();
        if (abs(sum - 1) > 1e-9) {
            throw new IllegalArgumentException("Total sum of the probabilities should equal 1.");
        }
    }

    private static void verifyProbabilityValues(List<Double> entries) {
        boolean areAllEntriesInValidRange = entries.stream().allMatch(value -> value != null && value > 0 && value < 1);

        if(!areAllEntriesInValidRange) {
            throw new IllegalArgumentException("Each probability value should be between 0 and 1 (inclusive).");
        }
    }

    private static <T> void checkForNulls(List<T> list, String listName) {
        if (list.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Provided " + listName + " list should not contain null elements.");
        }
    }
}
