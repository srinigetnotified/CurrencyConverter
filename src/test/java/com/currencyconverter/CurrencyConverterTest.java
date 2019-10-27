package com.currencyconverter;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CurrencyConverterTest {

    private static CurrencyUtil currencyUtil;

    @BeforeClass
    public static void setup() {
        currencyUtil = new CurrencyUtil();
        currencyUtil.populateCurrencyLookUpMatrix();
    }

    @Test
    public void shouldConvertCurrencyBasedOnDirectConversionType() {
        CurrencyConverter currencyConverter = new CurrencyConverter(Currency.AUD.toString(), Currency.USD.toString(), currencyUtil);
        BigDecimal currencyValue = currencyConverter.convertCurrencyValue();
        assertEquals(new BigDecimal("0.8371"), currencyValue);
    }

    @Test
    public void shouldConvertCurrencyBasedOnInvertedConversionType() {
        CurrencyConverter currencyConverter = new CurrencyConverter(Currency.USD.toString(), Currency.AUD.toString(), currencyUtil);
        BigDecimal currencyValue = currencyConverter.convertCurrencyValue();
        assertEquals(new BigDecimal("1.1946"), currencyValue);
    }

    @Test
    public void shouldConvertCurrencyBasedOnOneToOneConversionType() {
        CurrencyConverter currencyConverter = new CurrencyConverter(Currency.AUD.toString(), Currency.AUD.toString(), currencyUtil);
        BigDecimal currencyValue = currencyConverter.convertCurrencyValue();
        assertEquals(new BigDecimal(1), currencyValue);
    }

    @Test
    public void shouldConvertCurrencyBasedOnCrossViaCurrencyConversionType() {
        CurrencyConverter currencyConverter = new CurrencyConverter(Currency.AUD.toString(), Currency.JPY.toString(), currencyUtil);
        BigDecimal currencyValue = currencyConverter.convertCurrencyValue();
        assertEquals(new BigDecimal("100.4101"), currencyValue);
    }

    @Test(expected = InvalidCurrencyException.class)
    public void shouldThrowInvalidCurrencyException() {
        CurrencyConverter currencyConverter = new CurrencyConverter(Currency.AUD.toString(), "XYZ", currencyUtil);
        currencyConverter.convertCurrencyValue();
    }

    @Test
    public void testMainMethod(){
        String input = "CZK 363.10 in JPY";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        CurrencyConverterApplication.main(new String[]{});
    }
}
