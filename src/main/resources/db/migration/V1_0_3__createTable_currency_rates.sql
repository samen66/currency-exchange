CREATE TABLE currency_rates
(
    id              BIGSERIAL PRIMARY KEY,
    base_currency   VARCHAR(3),
    target_currency VARCHAR(3),
    rate            DECIMAL(19, 6),
    last_updated    DATE
);

-- Comments for the 'currency_rates' table
COMMENT ON TABLE currency_rates IS 'Stores historical and current exchange rates for various currency pairs, including the rate and the date on which it was valid. This table supports financial transactions and reporting by providing necessary currency conversion rates.';

-- Comments for columns in the 'currency_rates' table
COMMENT ON COLUMN currency_rates.id IS 'A unique identifier for each entry in the currency rates table, automatically generated by the database.';
COMMENT ON COLUMN currency_rates.rate IS 'The exchange rate value relative to the USD for the specified currency code, allowing for precise financial calculations.';
COMMENT ON COLUMN currency_rates.last_updated IS 'The date for which the exchange rate is applicable, ensuring that financial calculations use the correct historical or current rate.';
