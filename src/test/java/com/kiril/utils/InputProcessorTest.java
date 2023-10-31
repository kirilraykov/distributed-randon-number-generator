package com.kiril.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InputProcessorTest {

    List<Integer> allowedNumbers;
    List<Double> probabilities;

    @BeforeEach
    void setup() {
        allowedNumbers = List.of(-1, 0, 1, 2, 3);
        probabilities = List.of(0.01, 0.3, 0.58, 0.1, 0.01);
    }

    @Test
    void testValidateInputListsValid() {
        assertDoesNotThrow(() -> InputProcessor.validateInputLists(allowedNumbers, probabilities));
    }

    @Test
    void testValidateInputListsNullNumbers() {
        assertThrows(NullPointerException.class, () -> InputProcessor.validateInputLists(null, probabilities));
    }

    @Test
    void testValidateInputListsNullProbabilities() {
        assertThrows(NullPointerException.class, () -> InputProcessor.validateInputLists(allowedNumbers, null));
    }

    @Test
    void testValidateInputListsUnequalSizes() {
        probabilities = List.of(0.01, 0.3, 0.58);
        assertThrows(IllegalArgumentException.class, () -> InputProcessor.validateInputLists(allowedNumbers, probabilities));
    }

    @Test
    void testValidateInputListsContainNullValue() {
        probabilities = Arrays.asList(0.01, 0.3, 0.58, 0.1, 0.02);
        probabilities.set(1, null);
        assertThrows(IllegalArgumentException.class, () -> InputProcessor.validateInputLists(allowedNumbers, probabilities));
    }

    @Test
    void testValidateInputListsInvalidProbabilitySum() {
        probabilities = List.of(0.01, 0.3, 0.58, 0.1, 0.02);
        assertThrows(IllegalArgumentException.class, () -> InputProcessor.validateInputLists(allowedNumbers, probabilities));
    }

    @Test
    void testValidateProbabilityInvalidValues(){
        // Containing invalid value - negative number/out of range
        probabilities = Arrays.asList(-0.01, 0.3, 0.58, 0.1, 0.03);
        assertThrows(IllegalArgumentException.class, () -> InputProcessor.validateInputLists(allowedNumbers, probabilities));

        // Containing invalid value - probability = 1
        probabilities = Arrays.asList(1.0, 0.0, 0.0, 0.0, 0.0);
        assertThrows(IllegalArgumentException.class, () -> InputProcessor.validateInputLists(allowedNumbers, probabilities));

        // Containing invalid value - probability = 0
        probabilities = Arrays.asList(0.0, 0.3, 0.58, 0.1, 0.02);
        assertThrows(IllegalArgumentException.class, () -> InputProcessor.validateInputLists(allowedNumbers, probabilities));
    }

    @Test
    void testConvertInputListsToMap() {
        Map<Integer, Double> expected = Map.of(-1, 0.01, 0, 0.3, 1, 0.58, 2, 0.1, 3, 0.01);
        assertEquals(expected, InputProcessor.convertInputListsToMap(allowedNumbers, probabilities));
    }
}
