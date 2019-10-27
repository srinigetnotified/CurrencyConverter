package com.currencyconverter;

/**
 * CurrencyLookup object stores the cross currency matrix combination values
 */
public class CurrencyLookup {

    private Currency fromCurrency;
    private Currency toCurrency;
    private ConversionType conversionType;
    private String value;

    public CurrencyLookup(Currency fromCurrency, Currency toCurrency, ConversionType conversionType, String value) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.conversionType = conversionType;
        this.value = value;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public ConversionType getConversionType() {
        return conversionType;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "CurrencyLookup{" +
                "fromCurrency=" + fromCurrency +
                ", toCurrency=" + toCurrency +
                ", conversionType=" + conversionType +
                ", value='" + value + '\'' +
                '}';
    }
}
