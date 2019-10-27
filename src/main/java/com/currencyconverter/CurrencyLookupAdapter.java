package com.currencyconverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CurrencyLookupAdapter is a singleton object which stores the
 * cross currency matrix combination objects in  a list.
 * Also it provides the utility methods to add and find the currency lookup combination values.
 */
public class CurrencyLookupAdapter {

    private static CurrencyLookupAdapter lookupAdapter = null;
    // used to store CurrencyLookup objects
    List<CurrencyLookup> currencyLookupList = new ArrayList<>();

    private CurrencyLookupAdapter() {
    }

    public static CurrencyLookupAdapter instance() {
        if (null == lookupAdapter) {
            lookupAdapter = new CurrencyLookupAdapter();
        }
        return lookupAdapter;
    }

    /**
     * Adds CurrencyLookup object in currencyLookupList
     *
     * @param fromCurrency
     * @param toCurrency
     * @param conversionType
     * @param value
     */
    public void addCurrencyLookup(Currency fromCurrency, Currency toCurrency, ConversionType conversionType, String value) {
        currencyLookupList.add(new CurrencyLookup(fromCurrency, toCurrency, conversionType, value));
    }

    /**
     * Finds CurrencyLookup object from currencyLookupList based on the given fromCurrency and toCurrency
     *
     * @param fromCurrency
     * @param toCurrency
     * @return Optional<CurrencyLookup>
     */
    public Optional<CurrencyLookup> findCurrencyLookupMap(Currency fromCurrency, Currency toCurrency) {
        Optional<CurrencyLookup> currencyLookupOptional = currencyLookupList.stream().
                filter(input -> input.getFromCurrency().equals(fromCurrency)
                        && input.getToCurrency().equals(toCurrency)).findAny();
        return currencyLookupOptional;
    }

}
