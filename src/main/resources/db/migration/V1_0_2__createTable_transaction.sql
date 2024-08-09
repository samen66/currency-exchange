CREATE TABLE transactions
(
    id                 BIGSERIAL PRIMARY KEY,
    account_from       VARCHAR(255),
    account_to         VARCHAR(255),
    currency_shortname VARCHAR(3),
    sum                DECIMAL(19, 4),
    expense_category   VARCHAR(255),
    datetime           TIMESTAMP WITH TIME ZONE
);

COMMENT ON TABLE transactions IS 'Stores all financial transactions, recording details about money transferred from one account to another.';
COMMENT ON COLUMN transactions.id IS 'The primary key of the transactions table, uniquely identifying each transaction.';
COMMENT ON COLUMN transactions.account_from IS 'Identifier of the account from which money is debited. Represents the source of the transaction.';
COMMENT ON COLUMN transactions.account_to IS 'Identifier of the account to which money is credited. Represents the destination of the transaction.';
COMMENT ON COLUMN transactions.currency_shortname IS 'The ISO currency code for the transaction amount, such as USD, EUR, KZT, etc.';
COMMENT ON COLUMN transactions.sum IS 'The amount of money transferred in the transaction, specified in the currency denoted by currency_shortname.';
COMMENT ON COLUMN transactions.expense_category IS 'Category of the expense for this transaction, such as "utilities", "groceries", etc.';
COMMENT ON COLUMN transactions.datetime IS 'The date and time when the transaction occurred, including the time zone.';