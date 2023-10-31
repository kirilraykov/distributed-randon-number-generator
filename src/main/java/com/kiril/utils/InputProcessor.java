package com.kiril.utils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.abs;
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
    }

    public static Map<Integer, Double> convertInputListsToMap(List<Integer> randomNumbers, List<Double> probabilities) {
        return IntStream.range(0, randomNumbers.size())
                .boxed()
                .collect(Collectors.toMap(randomNumbers::get, probabilities::get));
    }

    public static void verifyNonNullInput(Object... objects) {
        requireNonNull(objects, "Provided input should NOT be null.");
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

    private static <T> void checkForNulls(List<T> list, String listName) {
        if (list.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Provided " + listName + " list should not contain null elements.");
        }
    }
}
