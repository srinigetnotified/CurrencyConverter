package com.currencyconverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Optional;

/**
 * CurrencyConverter class calculates the currency value
 * by converting the rate from fromCurrency to toCurrency
 */
public class CurrencyConverter {

    private final String fromCurrency;
    private final String toCurrency;
    private final HashMap<String, BigDecimal> currencyRateMap;
    private CurrencyLookupAdapter lookupAdapter = CurrencyLookupAdapter.instance();

    public CurrencyConverter(String fromCurrency, String toCurrency, CurrencyUtil currencyUtil) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.currencyRateMap = currencyUtil.getCurrencyRateMap();
    }

    /**
     * convertCurrencyValue method exposed outside for calculating the converted currency value
     *
     * @return
     * @throws InvalidCurrencyException
     */
    public BigDecimal convertCurrencyValue() throws InvalidCurrencyException {
        this.validateCurrency();
        return this.calculateCurrencyValue(Currency.valueOf(this.fromCurrency), Currency.valueOf(this.toCurrency));
    }

    /**
     * Finds the currency lookup object for the given currencies and calculates the conversion value.
     * Based on the conversion type currency value is calculated
     *
     * @param fromCurrency user provided from currency value
     * @param toCurrency user provided to currency value
     * @return BigDecimal calculated conversion currency value
     */
    private BigDecimal calculateCurrencyValue(Currency fromCurrency, Currency toCurrency) {
        Optional<CurrencyLookup> currencyLookupOptional = lookupAdapter.findCurrencyLookupMap(fromCurrency, toCurrency);
        BigDecimal currencyValue = null;
        if (currencyLookupOptional.isPresent()) {
            switch (currencyLookupOptional.get().getConversionType()) {
                case DIRECT:
                    currencyValue = currencyRateMap.get(fromCurrency.toString() + toCurrency.toString());
                    break;
                case INV:
                    currencyValue = currencyRateMap.get(toCurrency.toString() + fromCurrency.toString());
                    currencyValue = new BigDecimal(1.0).divide(currencyValue, 4, RoundingMode.HALF_UP);
                    break;
                case CROSS_VIA_CURRENCY:
                    BigDecimal medianCurrencyValue = calculateCurrencyValue(fromCurrency, Currency.valueOf(currencyLookupOptional.get().getValue()));
                    currencyValue = medianCurrencyValue.multiply(calculateCurrencyValue(Currency.valueOf(currencyLookupOptional.get().getValue()), toCurrency)).setScale(4, RoundingMode.HALF_UP);
                    break;
                case ONE_TO_ONE:
                    currencyValue = new BigDecimal(1.0);
                    break;
                default:
                    return null;
            }
        }
        return currencyValue;
    }

    /**
     * Validates the given from and to currency available in application or not.
     * @throws InvalidCurrencyException if currency is not available
     */
    private void validateCurrency() {
        try {
            Currency.valueOf(this.fromCurrency);
            Currency.valueOf(this.toCurrency);
        } catch (IllegalArgumentException e) {
            throw new InvalidCurrencyException("Unable to find rate for " + this.fromCurrency + "/" + this.toCurrency);
        }
    }
}
