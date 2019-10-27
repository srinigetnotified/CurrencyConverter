package com.currencyconverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CurrencyUtil object acts as a utility class for currency lookup and currency rates.
 *
 */
public class CurrencyUtil {

    private CurrencyLookupAdapter lookupAdapter = CurrencyLookupAdapter.instance();
    // Currency rates are stored in Hashmap
    private HashMap<String, BigDecimal> currencyRateMap;

    public CurrencyUtil() {
        this.currencyRateMap = new HashMap<>();
        this.currencyRateMap.put("AUDUSD", new BigDecimal(0.8371).setScale(4, RoundingMode.HALF_UP));
        this.currencyRateMap.put("CADUSD", new BigDecimal(0.8711).setScale(4, RoundingMode.HALF_UP));
        this.currencyRateMap.put("USDCNY", new BigDecimal(6.1715).setScale(4, RoundingMode.HALF_UP));
        this.currencyRateMap.put("EURUSD", new BigDecimal(1.2315).setScale(4, RoundingMode.HALF_UP));
        this.currencyRateMap.put("GBPUSD", new BigDecimal(1.5683).setScale(4, RoundingMode.HALF_UP));
        this.currencyRateMap.put("NZDUSD", new BigDecimal(0.7750).setScale(4, RoundingMode.HALF_UP));
        this.currencyRateMap.put("USDJPY", new BigDecimal(119.95).setScale(4, RoundingMode.HALF_UP));
        this.currencyRateMap.put("EURCZK", new BigDecimal(27.6028).setScale(4, RoundingMode.HALF_UP));
        this.currencyRateMap.put("EURDKK", new BigDecimal(7.4405).setScale(4, RoundingMode.HALF_UP));
        this.currencyRateMap.put("EURNOK", new BigDecimal(8.6651).setScale(4, RoundingMode.HALF_UP));
    }

    /**
     * Iterates the list of given currencies and populates the matrix data based on the following logic.
     * If from and to currency are same CurrencyLookup object combination conversion type stored as ONE_TO_ONE
     * If from and to currency found in currencyRateMap then CurrencyLookup object combination conversion type stored as DIRECT
     * If to and from currency found in currencyRateMap then CurrencyLookup object combination conversion type stored as INV
     *
     * else
     *
     * find the currency rates in currencyRateMap where currency combination ends with from currency as fromCurrencyEndsWithSet
     * find the currency rates in currencyRateMap where currency combination ends with to currency as toCurrencyEndsWithSet
     * If both fromCurrencyEndsWithSet and toCurrencyEndsWithSet has values which starts with same currency then that particular filtered
     * starting currency will be used as value in CROSS_VIA_CURRENCY object
     * else
     * currency value stored as USD in CROSS_VIA_CURRENCY object.
     */
    public void populateCurrencyLookUpMatrix() {
        for (Currency fromCurrency : Currency.values()) {
            for (Currency toCurrency : Currency.values()) {
                if (fromCurrency.equals(toCurrency)) {
                    lookupAdapter.addCurrencyLookup(fromCurrency, toCurrency, ConversionType.ONE_TO_ONE, "O");
                } else if (null != currencyRateMap.get(fromCurrency.toString() + toCurrency.toString())) {
                    lookupAdapter.addCurrencyLookup(fromCurrency, toCurrency, ConversionType.DIRECT, "D");
                } else if (null != currencyRateMap.get(toCurrency.toString() + fromCurrency.toString())) {
                    lookupAdapter.addCurrencyLookup(fromCurrency, toCurrency, ConversionType.INV, "I");
                } else {
                    Set<String> fromCurrencyEndsWithSet = currencyRateMap.keySet().stream().
                            filter(input -> input.endsWith(fromCurrency.toString())).collect(Collectors.toSet());
                    Set<String> toCurrencyEndsWithSet = currencyRateMap.keySet().stream().
                            filter(input -> input.endsWith(toCurrency.toString())).collect(Collectors.toSet());
                    if (!fromCurrencyEndsWithSet.isEmpty() && !toCurrencyEndsWithSet.isEmpty()) {
                        // Identify one or more set values starts with same currency
                        boolean bothHasSameStartingCurrency = false;
                        for (Currency currency : Currency.values()) {
                            Optional<String> currencyStartsForFromCurrency = fromCurrencyEndsWithSet.stream().filter(input -> input.startsWith(currency.toString())).findAny();
                            Optional<String> currencyStartsForToCurrency = toCurrencyEndsWithSet.stream().filter(input -> input.startsWith(currency.toString())).findAny();
                            if (currencyStartsForFromCurrency.isPresent() && currencyStartsForToCurrency.isPresent()) {
                                bothHasSameStartingCurrency = true;
                                lookupAdapter.addCurrencyLookup(fromCurrency, toCurrency, ConversionType.CROSS_VIA_CURRENCY, currency.toString());
                            }
                        }
                        if (!bothHasSameStartingCurrency)
                            lookupAdapter.addCurrencyLookup(fromCurrency, toCurrency, ConversionType.CROSS_VIA_CURRENCY, Currency.USD.toString());

                    } else {
                        lookupAdapter.addCurrencyLookup(fromCurrency, toCurrency, ConversionType.CROSS_VIA_CURRENCY, Currency.USD.toString());
                    }
                }
            }
        }
    }

    /**
     * @return currencyRateMap
     */
    public HashMap<String, BigDecimal> getCurrencyRateMap() {
        return currencyRateMap;
    }
}
