package com.currencyconverter;

/**
 * Currency Decimal Precision Map constants
 */
public enum CurrencyDecimalMap {

    AUD(Currency.AUD, 2),
    CAD(Currency.CAD, 2),
    CNY(Currency.CNY, 2),
    CZK(Currency.CZK, 2),
    DKK(Currency.DKK, 2),
    EUR(Currency.EUR, 2),
    GBP(Currency.GBP, 2),
    JPY(Currency.JPY, 0),
    NOK(Currency.NOK, 2),
    NZD(Currency.NZD, 2),
    USD(Currency.USD, 2);

    private Currency currency;
    private int decimal;

    CurrencyDecimalMap(Currency currency, int decimal) {
        this.currency = currency;
        this.decimal = decimal;
    }

    public int getDecimal() {
        return decimal;
    }
}
