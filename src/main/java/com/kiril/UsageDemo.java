package com.kiril;

import com.kiril.generator.DistributionRandomNumberGenerator;
import com.kiril.generator.RandomNumberGenerator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.IntStream;

import static com.kiril.utils.InputProcessor.*;

public class UsageDemo {

    public static void main(String[] args) {
        // As per requirement, the random generator should work based on the provided allowed numbers and their probability lists
        List<Integer> allowedNumbers = List.of(-1, 0, 1, 2, 3);
        List<Double> probabilities = List.of(0.01, 0.3, 0.58, 0.1, 0.01);
        validateInputLists(allowedNumbers, probabilities);

        // Convert the valid lists to numbers distribution map
        Map<Integer, Double> numbersDistribution = convertInputListsToMap(allowedNumbers, probabilities);

        // Initialize the number generator based on the numbers distribution map (allowed numbers and their probabilities)
        RandomNumberGenerator numberGenerator = new DistributionRandomNumberGenerator(numbersDistribution);

        // Run the number generator n times and save into generated number + # of occurrences map
        Map<Integer, Integer> numberOccurrencesMap = new HashMap<>();
        int iterations = 10000;
        IntStream.range(0, iterations)
                .map(i -> numberGenerator.nextNum())
                .forEach(num -> numberOccurrencesMap.put(num, (numberOccurrencesMap.get(num) == null) ? 1 : numberOccurrencesMap.get(num) + 1));

        // Calculate probability and print out results
        numberOccurrencesMap.forEach((key, value) -> {
            double probability = value.floatValue() / iterations;
            BigDecimal scaledProbability = new BigDecimal(probability).setScale(5, RoundingMode.HALF_UP);

            System.out.printf("Number of occurrences for %d: %d. Probability: ~%.5f\n", key, value, scaledProbability);
        });
    }

}
