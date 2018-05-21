DROP TABLE people IF EXISTS;

CREATE TABLE stock_quote  (
    stock_quote_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    date DATE,
    open DOUBLE,
    high DOUBLE,
    low DOUBLE,
    close DOUBLE,
    adjusted_close DOUBLE,
    volume BIGINT    
);