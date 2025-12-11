-- Create Portfolio table
CREATE TABLE portfolio (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500)
);

-- Create Income table
CREATE TABLE income (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    amount DECIMAL(19,2) NOT NULL,
    description VARCHAR(255),
    type VARCHAR(100),
    date DATE NOT NULL,
    portfolio_id BIGINT NOT NULL
);

-- Create Expense table
CREATE TABLE expense (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    amount DECIMAL(19,2) NOT NULL,
    description VARCHAR(255),
    type VARCHAR(100),
    date DATE NOT NULL,
    portfolio_id BIGINT NOT NULL
);

-- Create Investment table
CREATE TABLE investment (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    amount DECIMAL(19,2) NOT NULL,
    description VARCHAR(255),
    asset VARCHAR(100),
    date DATE NOT NULL,
    portfolio_id BIGINT NOT NULL
);

-- Create Saving table
CREATE TABLE saving (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    amount DECIMAL(19,2) NOT NULL,
    description VARCHAR(255),
    type VARCHAR(100),
    date DATE NOT NULL,
    portfolio_id BIGINT NOT NULL
);

-- Add foreign key constraints
ALTER TABLE income
    ADD CONSTRAINT fk_income_portfolio FOREIGN KEY (portfolio_id) REFERENCES portfolio(id);

ALTER TABLE expense
    ADD CONSTRAINT fk_expense_portfolio FOREIGN KEY (portfolio_id) REFERENCES portfolio(id);

ALTER TABLE investment
    ADD CONSTRAINT fk_investment_portfolio FOREIGN KEY (portfolio_id) REFERENCES portfolio(id);

ALTER TABLE saving
    ADD CONSTRAINT fk_saving_portfolio FOREIGN KEY (portfolio_id) REFERENCES portfolio(id);