-- Enable pg_trgm extension for full-text search
CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- Create cards table
CREATE TABLE IF NOT EXISTS cards (
    id SERIAL PRIMARY KEY,
    card_number VARCHAR(50),
    set_code VARCHAR(10) NOT NULL,
    name VARCHAR(255) NOT NULL,
    card_type VARCHAR(255),
    mana_cost VARCHAR(255),
    power VARCHAR(10),
    toughness VARCHAR(10),
    rules_text TEXT,
    flavor_text TEXT,
    original_text TEXT,
    original_type VARCHAR(255),
    cn BIGINT,
    card_name VARCHAR(255),
    rarity VARCHAR(50),
    card_class_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for efficient queries
CREATE INDEX IF NOT EXISTS idx_cards_name ON cards(name);
CREATE INDEX IF NOT EXISTS idx_cards_set_code ON cards(set_code);
CREATE INDEX IF NOT EXISTS idx_cards_cn ON cards(cn);
CREATE INDEX IF NOT EXISTS idx_cards_card_number ON cards(card_number);

-- Full-text search indexes using trigram
CREATE INDEX IF NOT EXISTS idx_cards_name_trgm ON cards USING gin(name gin_trgm_ops);
CREATE INDEX IF NOT EXISTS idx_cards_rules_text_trgm ON cards USING gin(rules_text gin_trgm_ops);

-- Composite index for common queries
CREATE INDEX IF NOT EXISTS idx_cards_set_name ON cards(set_code, name);
