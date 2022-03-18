/*
 * Copyright (c) Gabriel Sterz 2022
 */

package de.sterzsolutions.ccmavenfx.util;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.exceptions.ExchangeException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {

    @Test
    void validateBinanceApi_testTrue() {
        ValidationUtil util = new ValidationUtil();
        try {
            assertTrue(util.validateBinanceApi("A8K49nG5hWn8blUOrPNkhpZecnSZdYAiJK6oSRc9roYJbCYcUKZQkd4q72ApwQpW", "gDYaQ64Qbubx3YBsB76ogYAEIe6lEq02QxjlpSQjEAXxftdSixU2A0dWXzUMNO3J"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    void validateBinanceApi_testFalse() {
        ValidationUtil util = new ValidationUtil();
        try {
            assertFalse(util.validateBinanceApi("jimbo", "jumbo"));
        } catch (IOException | ExchangeException e) {
            e.printStackTrace();
        }
    }
}
