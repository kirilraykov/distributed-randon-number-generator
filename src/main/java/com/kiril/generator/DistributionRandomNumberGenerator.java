package com.kiril.generator;

import java.util.Map;
import java.util.TreeMap;

import static com.kiril.utils.InputProcessor.verifyNonNullInput;
import static com.kiril.utils.InputProcessor.verifyNonNullMapEntries;

public class DistributionRandomNumberGenerator implements RandomNumberGenerator {

    private final TreeMap<Double, Integer> cumulativeDistribution;

    /**
     * Calculate the cumulative probabilities once when the distribution is set and store them in TreeMap.
     * @param distribution - map of each allowed number and its cumulative probability
     */
    public DistributionRandomNumberGenerator(Map<Integer, Double> distribution) {
        verifyNonNullInput(distribution);
        verifyNonNullMapEntries(distribution);

        cumulativeDistribution = new TreeMap<>();
        double cumulativeProbability = 0.0;

        for (Map.Entry<Integer, Double> entry : distribution.entrySet()) {
            cumulativeProbability += entry.getValue();
            cumulativeDistribution.put(cumulativeProbability, entry.getKey());
        }
    }

    /**
     * By using cumulative probabilities as keys and numbers as values,
     * we can take advantage of the ceilingEntry() method of TreeMap,
     * which returns the least key greater than or equal to the given key.
     * @return Generated random number based on the distribution requirements for current Generator instance.
     */
    @Override
    public int nextNum() {
        double rand = Math.random();
        Map.Entry<Double, Integer> entry = cumulativeDistribution.ceilingEntry(rand);
        return entry.getValue();
    }
}
