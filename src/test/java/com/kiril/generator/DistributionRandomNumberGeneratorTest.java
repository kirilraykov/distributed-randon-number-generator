package com.kiril.generator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DistributionRandomNumberGeneratorTest {

    Map<Integer, Double> numbersDistribution;
    RandomNumberGenerator randomNumberGenerator;

    @BeforeAll
    void setup(){
        numbersDistribution = Map.of(
                -1, 0.01,
                0, 0.3,
                1, 0.58,
                2, 0.1,
                3, 0.01);
        randomNumberGenerator = new DistributionRandomNumberGenerator(numbersDistribution);
    }

    /**
     * Perform basic test for the main method for generating random number - assert no exceptions thrown.
     */
    @Test
    public void testNextNum(){
        assertDoesNotThrow(() -> randomNumberGenerator.nextNum());
    }

    /**
     * Verify for any 1000 randomly generated numbers are all contained inside the initial distribution key set.
     */
    @Test
    public void testAllowedNextNumGeneration(){
        int randNumbersToGenerate = 1000;
        List<Integer> generatedNumbers = IntStream.range(0, randNumbersToGenerate)
                .map(i -> randomNumberGenerator.nextNum())
                .boxed()
                .collect(Collectors.toList());

        generatedNumbers.forEach(
                number -> assertTrue(numbersDistribution.containsKey(number),
                        "The Generated number is NOT contained in the original allowed numbers list")
        );
    }

    /**
     * Following the Law of Large Numbers, generate random numbers based on the main distribution list and assert their
     * probabilities match the expected ones.
     * Note that this may NOT be suited for unit test, but instead as integration test as it allows a certain level of flakiness.
     */
    @Test
    public void testNextNumGenerationProbabilities(){
        int iterations = 100000;
        Map<Integer, Integer> numberOccurrencesMap = new HashMap<>();
        IntStream.range(0, iterations)
                .map(i -> randomNumberGenerator.nextNum())
                .forEach(num -> numberOccurrencesMap.put(num, (numberOccurrencesMap.get(num) == null) ? 1 : numberOccurrencesMap.get(num) + 1));

        numberOccurrencesMap.forEach((key, value) -> {
            double probability = value.floatValue() / iterations;
            BigDecimal scaledProbability = new BigDecimal(probability).setScale(2, RoundingMode.HALF_UP);

            assertEquals(numbersDistribution.get(key), scaledProbability.doubleValue());
        });
    }

    /**
     * If some numbers almost never occur, there might be an issue with the generation logic.
     */
    @Test
    public void testAllNumbersOccur(){
        int iterations = 100000;
        Set<Integer> generatedNumbers = IntStream.range(0, iterations)
                .mapToObj(i -> randomNumberGenerator.nextNum())
                .collect(Collectors.toSet());

        assertEquals(numbersDistribution.keySet(), generatedNumbers, "Not all numbers from the distribution occurred.");
    }

    @Test
    public void testNullDistribution() {
        assertThrows(NullPointerException.class, () -> new DistributionRandomNumberGenerator(null));
    }

    @Test
    public void testDistributionContainingNullValues() {
        Map<Integer, Double> nullValuesNumbersDistribution = new HashMap<>();
        nullValuesNumbersDistribution.put(0, 0.5);
        nullValuesNumbersDistribution.put(1, null);
        nullValuesNumbersDistribution.put(3, 0.5);

        assertThrows(IllegalArgumentException.class, () -> new DistributionRandomNumberGenerator(nullValuesNumbersDistribution));
    }
}
