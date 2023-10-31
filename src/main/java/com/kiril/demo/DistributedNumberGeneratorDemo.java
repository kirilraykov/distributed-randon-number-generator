package com.kiril.demo;

import com.kiril.generator.DistributionRandomNumberGenerator;
import com.kiril.generator.RandomNumberGenerator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kiril.utils.InputProcessor.*;

public class DistributedNumberGeneratorDemo {

    public static void main(String[] args) {
        // As per requirement, the random generator should work based on the provided allowed numbers and their probability lists
        Scanner scanner = new Scanner(System.in);

        // Read a line for the list of allowed numbers
        System.out.println("Enter a list of integers separated by spaces:");
        String intLine = scanner.nextLine();
        List<Integer> allowedNumbers = Arrays.stream(intLine.trim().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        // Read a line for the list of probabilities
        System.out.println("Enter a list of doubles separated by spaces:");
        String doubleLine = scanner.nextLine();
        List<Double> probabilities = Arrays.stream(doubleLine.trim().split("\\s+"))
                .map(Double::parseDouble)
                .collect(Collectors.toList());

        validateInputLists(allowedNumbers, probabilities);

        // Convert the valid lists to numbers distribution map
        Map<Integer, Double> numbersDistribution = convertInputListsToMap(allowedNumbers, probabilities);

        // Initialize the number generator based on the numbers distribution map (allowed numbers and their probabilities)
        RandomNumberGenerator numberGenerator = new DistributionRandomNumberGenerator(numbersDistribution);

        // Run the number generator n times and save into generated number + # of occurrences map
        Map<Integer, Integer> numberOccurrencesMap = new HashMap<>();
        int iterations = 10000;
        System.out.printf("Generating %d random numbers using the provided numbers distribution...\n", iterations);
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
