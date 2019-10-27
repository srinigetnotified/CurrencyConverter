package com.currencyconverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 *
 * CurrencyConverterApplication runs the currency converter application.
 *
 */
public class CurrencyConverterApplication {

    public static void main(String[] args) {
        try {

            System.out.println("Plase enter input as given below \nExample : AUD 100.00 in USD");
            // System scanner to collect the user input
            Scanner in = new Scanner(System.in);
            in.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            String input = in.nextLine();

            String[] inputArray = input.split("\\s");
            String fromCurrency = inputArray[0];
            String amount = inputArray[1];
            String toCurrency = inputArray[3];

            // Currency Util object used for population currnecy lookup matrix
            CurrencyUtil currencyUtil = new CurrencyUtil();
            currencyUtil.populateCurrencyLookUpMatrix();
            // CurrencyConverter is calculator object converts the currency value.
            CurrencyConverter currencyConverter = new CurrencyConverter(fromCurrency, toCurrency, currencyUtil);
            BigDecimal currencyValue = currencyConverter.convertCurrencyValue();
            if (null != currencyValue) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(fromCurrency).append(" ").append(amount).append(" = ").append(toCurrency).append(" ")
                        .append(currencyValue.multiply(new BigDecimal(amount)).setScale(CurrencyDecimalMap.valueOf(toCurrency).getDecimal(), RoundingMode.HALF_UP));
                System.out.println(stringBuffer.toString());
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Please enter valid input as given below : \nExample : AUD 100.00 in USD");
        } catch (InvalidCurrencyException e) {
            System.out.println(e.getMessage());
        }
    }
}
