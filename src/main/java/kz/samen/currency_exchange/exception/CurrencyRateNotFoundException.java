package kz.samen.currency_exchange.exception;

public class CurrencyRateNotFoundException extends Exception{
    public CurrencyRateNotFoundException(String message) {
        super(message);
    }
}
